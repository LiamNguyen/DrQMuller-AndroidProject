package DrQMuller.DrQMuller.Controller;

import android.content.Context;

import DrQMuller.DrQMuller.Model.BackgroundTask;
import DrQMuller.DrQMuller.Model.DatabaseObserver;
import DrQMuller.DrQMuller.Model.DatabaseQuery;
import DrQMuller.DrQMuller.Model.ModelAccount;
import DrQMuller.DrQMuller.Model.ModelUserInfo;

/**
 * Created by ADMIN on 25-Oct-16.
 */
public class Controller {
    private ModelUserInfo userInfo;
    private ModelAccount account;
    private DatabaseQuery databaseQuery;

    private static Controller ourInstance = new Controller();

    public static Controller getInstance() {
        return ourInstance;
    }

    private Controller() {
        userInfo = new ModelUserInfo();
        account = new ModelAccount();
        databaseQuery = new DatabaseQuery();
    }

    public ModelUserInfo getUserInfo(){
        return userInfo;
    }

    public ModelAccount getAccount(){
        return account;
    }

    public DatabaseQuery getDatabaseQuery(){
        return databaseQuery;
    }

    public void setRequestData(Context ctxt, DatabaseObserver ob, String url, String data){
        new BackgroundTask(ctxt, ob).execute(url, data);
    }
}
