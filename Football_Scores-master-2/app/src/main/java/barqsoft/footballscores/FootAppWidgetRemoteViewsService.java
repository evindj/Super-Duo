package barqsoft.footballscores;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import barqsoft.footballscores.service.myFetchService;

/**
 * Created by evindj on 12/15/15.
 */
public class FootAppWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return  new FootWidgetDataProvider(getApplicationContext(),intent);
    }
}
