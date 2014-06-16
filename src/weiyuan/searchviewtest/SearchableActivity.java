package weiyuan.searchviewtest;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SearchableActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchable);
		System.out.println("On Create");
	}
	
	
	@Override
	protected void onNewIntent(Intent intent) {
		System.out.println("OnNewIntent");
		Intent newIntent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(newIntent.getAction())) {
	      String query = newIntent.getStringExtra(SearchManager.QUERY);
	      doMySearch(query);
	    }
		
		
		super.onNewIntent(intent);
	}
	
	
	

	private void doMySearch(String query)
	{
		System.out.println(query);
	}
	
}
