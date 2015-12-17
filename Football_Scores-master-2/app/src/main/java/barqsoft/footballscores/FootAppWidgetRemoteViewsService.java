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
        return  new RemoteViewsFactory() {
            public static final int COL_HOME = 3;
            public static final int COL_AWAY = 4;
            public static final int COL_HOME_GOALS = 6;
            public static final int COL_AWAY_GOALS = 7;
            public static final int COL_DATE = 1;
            public static final int COL_LEAGUE = 5;
            public static final int COL_MATCHDAY = 9;
            public static final int COL_ID = 8;
            public static final int COL_MATCHTIME = 2;
            public double detail_match_id = 0;
            private String FOOTBALL_SCORES_HASHTAG = "#Football_Scores";
            private Cursor data = null;
            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                if(data != null){
                    data.close();
                }
                final long identityToken = Binder.clearCallingIdentity();
                Uri uri = DatabaseContract.scores_table.buildScoreWithDate();
                data = getContentResolver().query(uri,null,null,null,null);
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if(data!=null){
                    data.close();
                    data= null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
               if(position == AdapterView.INVALID_POSITION || data==null || !data.moveToPosition(position)){
                   return null;
               }
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.scores_list_item);
                String match_day = Utilies.getMatchDay(data.getInt(COL_MATCHDAY), data.getInt(COL_LEAGUE));
                views.setTextViewText(R.id.matchday_textview,match_day);
                String league = Utilies.getLeague(data.getInt(COL_LEAGUE));
                views.setTextViewText(R.id.league_textview,league);
                views.setTextViewText(R.id.home_name,data.getColumnName(COL_HOME));
                views.setTextViewText(R.id.away_name, data.getColumnName(COL_AWAY));
                views.setTextViewText(R.id.data_textview, data.getString(COL_MATCHTIME));
                views.setTextViewText(R.id.score_textview, Utilies.getScores(data.getInt(COL_HOME_GOALS), data.getInt(COL_AWAY_GOALS)));
                views.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(data.getString(COL_HOME)));
                views.setImageViewResource(R.id.away_crest, Utilies.getTeamCrestByTeamName(data.getString(COL_AWAY)));
                final Intent fillInIntent = new Intent();
                // setup onclick here but test first

               // Button share_button = (Button) v.findViewById(R.id.share_button);
                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                Intent service_start = new Intent(getApplicationContext(), myFetchService.class);
               getApplication().startService(service_start);
               return  new RemoteViews(getPackageName(), R.layout.foot_appwidget);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
               return  position;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }
        };
    }
}
