package weiyuan.searchviewtest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends ListActivity implements SearchView.OnQueryTextListener {
	
	
	private TextView textView = null;
	private String[] dataArray = null;
	private ArrayList<Device> deviceList = null;
	private List<String> dataList = null;
	private List<? extends Map<String,Object>> dataList2 = null;
	private ArrayAdapter<String> mArrayAdapter = null;
	private SimpleAdapter mSimpleAdapter = null;
	private MyAdapter mAdapter = null;

	
	private static final Comparator<Map<String,?>> SORT = new Comparator<Map<String,?>>(){

		@Override
		public int compare(Map<String, ?> lhs,
				Map<String, ?> rhs) {
			
			String l1 = (String) lhs.get("Location");
			String l2 = (String) rhs.get("Location");
			
			Integer f1 = (Integer) lhs.get("Fault");
			Integer f2 = (Integer) rhs.get("Fault");
			
			int firstComp = f1.compareTo(f2);
			
			return 	(firstComp == 0? l1.compareTo(l2) : firstComp);	
		}
		
		
		
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textView = (TextView) findViewById(R.id.hello_textView);
	
		dataArray = new String[] {"Monday Afternoon","Tuesday Morning","Wednesday Evening","Thursday Dinner","Friday Lunch","Saturday Breakfast","Sunday Snack"};
		dataList = new ArrayList<String>(Arrays.asList(dataArray));
		
		Collections.sort(dataList);
		mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
		
		deviceList = getDeviceList();
		//Collections.sort(deviceList);
		
		dataList2 = getDataList(deviceList);
		/*Collections.sort(dataList2,new Comparator<Map<String,?>>(){

			@Override
			public int compare(Map<String, ?> lhs,
					Map<String, ?> rhs) {
				
				String l1 = (String) lhs.get("Location");
				String l2 = (String) rhs.get("Location");
				
				Integer f1 = (Integer) lhs.get("Fault");
				Integer f2 = (Integer) rhs.get("Fault");
				
				int firstComp = f1.compareTo(f2);
				
				return 	(firstComp == 0? l1.compareTo(l2) : firstComp);	
			}

		
			
			
			
			
		});*/
		
		mAdapter = new MyAdapter(this, dataList2, R.layout.row_layout, new String[]{"Location","Fault"}, new int[]{R.id.textView1,R.id.textView2});
		mAdapter.sort(SORT);
		
		setListAdapter(mAdapter);
		
		
		
		
	}
	@Override
	protected void onNewIntent(Intent intent) {
		System.out.println("OnNewIntent");
		setIntent(intent);
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
		textView.setText(query);
		if(dataList.contains(query))
			dataList.remove(query);
		mArrayAdapter.notifyDataSetChanged();
	}
	

	



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search_menu, menu);
		
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
	    
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName())); // add searchable.xml configure file to searchView
	    searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
	    searchView.setQueryHint("Search Devices");
	    searchView.setOnQueryTextListener(this);
	    
	   	return true;
	}
	@Override
	public boolean onQueryTextChange(String query) {
		if(query!=null){
			mAdapter.getFilter().filter(query);
			System.out.println(query);
		}
		return true;
	}
	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	private List<? extends Map<String,Object>>getDataList(List<Device> deviceList)
	{
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		
		for(int i=0; i<deviceList.size();i++){
			HashMap<String,Object> map = new HashMap<String,Object>();
			//map.put("img", R.drawable.ic_launcher);
			map.put("Location",deviceList.get(i).getName());
			map.put("Fault",deviceList.get(i).getFaulty());
			
			list.add(map);
		
		}
		
		
		
		
		return list;
		
		
		
		
	}
	private ArrayList<Device> getDeviceList()
	{
		ArrayList<Device> list = new ArrayList<Device>();
		
		Device device = new Device("Z Floor",19);
		list.add(device);
		
		device = new Device("T Floor",0);
		list.add(device);
		
		device = new Device("B Floor",10);
		list.add(device);
		
		device = new Device("E Floor",2);
		list.add(device);
		
		device = new Device("Z Floor",5);
		list.add(device);
		
		device = new Device("M Floor",19);
		list.add(device);
		
		device = new Device("Mata Tea Floor",19);
		list.add(device);
		
		
		return list;
		
		
		
	}
	
}
