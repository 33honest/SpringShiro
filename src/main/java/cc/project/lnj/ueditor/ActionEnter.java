package cc.project.lnj.ueditor;

import cc.project.lnj.ueditor.define.ActionMap;
import cc.project.lnj.ueditor.define.AppInfo;
import cc.project.lnj.ueditor.define.BaseState;
import cc.project.lnj.ueditor.define.State;
import cc.project.lnj.ueditor.hunter.FileManager;
import cc.project.lnj.ueditor.hunter.ImageHunter;
import cc.project.lnj.ueditor.upload.Uploader;
import org.json.JSONException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ActionEnter {
	
	private HttpServletRequest request = null;
	
	private String rootPath = null;

	private String staticPath = null;

	private String projectPath = null;

	private String actionType = null;
	
	private ConfigManager configManager = null;

	public ActionEnter ( HttpServletRequest request, String rootPath,String staticPath,String projectPath ) {
		
		this.request = request;
		this.rootPath = rootPath;

		this.staticPath = staticPath;
		this.projectPath = projectPath;

		this.actionType = request.getParameter( "action" );
		this.configManager = ConfigManager.getInstance( this.rootPath,this.staticPath,this.projectPath);
		
	}
	
	public String exec () {
		
		String callbackName = this.request.getParameter("callback");
		
		if ( callbackName != null ) {

			if ( !validCallbackName( callbackName ) ) {
				return new BaseState( false, AppInfo.ILLEGAL ).toJSONString();
			}
			
			return callbackName+"("+this.invoke()+");";
			
		} else {
			return this.invoke();
		}

	}
	
	public String invoke() {
		
		if ( actionType == null || !ActionMap.mapping.containsKey( actionType ) ) {
			return new BaseState( false, AppInfo.INVALID_ACTION ).toJSONString();
		}
		
		if ( this.configManager == null || !this.configManager.valid() ) {
			return new BaseState( false, AppInfo.CONFIG_ERROR ).toJSONString();
		}
		
		State state = null;
		
		int actionCode = ActionMap.getType( this.actionType );
		
		Map<String, Object> conf = null;
		
		try {
			switch ( actionCode ) {
			
				case ActionMap.CONFIG:
					return this.configManager.getAllConfig().toString();
					
				case ActionMap.UPLOAD_IMAGE:
				case ActionMap.UPLOAD_SCRAWL:
				case ActionMap.UPLOAD_VIDEO:
				case ActionMap.UPLOAD_FILE:
					conf = this.configManager.getConfig( actionCode );
					state = new Uploader( request, conf ).doExec();
					break;
					
				case ActionMap.CATCH_IMAGE:
					conf = configManager.getConfig( actionCode );
					String[] list = this.request.getParameterValues( (String)conf.get( "fieldName" ) );
					state = new ImageHunter( conf ).capture( list );
					break;
					
				case ActionMap.LIST_IMAGE:
				case ActionMap.LIST_FILE:
					conf = configManager.getConfig( actionCode );
					int start = this.getStartIndex();
					state = new FileManager( conf ).listFile( start );
					break;
					
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return state.toJSONString();
		
	}
	
	public int getStartIndex () {
		
		String start = this.request.getParameter( "start" );
		
		try {
			return Integer.parseInt( start );
		} catch ( Exception e ) {
			return 0;
		}
		
	}
	
	/**
	 * callback参数验证
	 */
	public boolean validCallbackName ( String name ) {
		
		if ( name.matches( "^[a-zA-Z_]+[\\w0-9_]*$" ) ) {
			return true;
		}
		
		return false;
		
	}
	
}