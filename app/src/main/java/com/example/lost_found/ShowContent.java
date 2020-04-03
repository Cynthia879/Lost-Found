package com.example.lost_found;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowContent extends AppCompatActivity {
    Intent StoM;
    Intent StoD;
    Intent getLtoS;
    String userid;
    String result;
    String keyword;
    JSONArray jarray;
    String Showtype;
    ListView listView;
    int Count_lost,Count_found,Count_all;
    int lostArr[],foundArr[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_content);
        StoM=new Intent(this,MyPage.class);
        StoD=new Intent(this,DetailContent.class);
        getLtoS=getIntent();
        userid=getLtoS.getStringExtra("userid");
        String url="http://192.168.0.103:8080/searchContent.php";
        result=null;
        JSONObject jsonobj=new JSONObject();
        try {
            jsonobj.put("action","getContent");
            result=MyThread.Show(url,jsonobj,this,result);
            /*Toast.makeText(this, result, Toast.LENGTH_SHORT).show();*/
            if(!result.equals("no data")){
                result=result.substring(0,result.length()-1);
                result+="]";
            }else{
                result=null;
            }
            System.out.println(result);
        } catch (JSONException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            getJarray(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        listView = (ListView) findViewById(R.id.postlist);//在视图中找到ListView
        Showtype="all";
        myAdapter myAdapter=new myAdapter();
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    JSONObject jsonObject=jarray.getJSONObject(i);
                    String dtitle=jsonObject.getString("title");
                    String ditem=jsonObject.getString("item");
                    String dtype=jsonObject.getString("type");
                    String dtime=jsonObject.getString("time");
                    String dlocation=jsonObject.getString("location");
                    String ddescribe=jsonObject.getString("describe");

                    StoD.putExtra("title",dtitle);
                    StoD.putExtra("item",ditem);
                    StoD.putExtra("type",dtype);
                    StoD.putExtra("time",dtime);
                    StoD.putExtra("location",dlocation);
                    StoD.putExtra("describe",ddescribe);
                    StoD.putExtra("userid",userid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(StoD);            }
        });
    }

    public void ShowLOST(View V){
        listView = (ListView) findViewById(R.id.postlist);//在视图中找到ListView
        Showtype="lost";
        myAdapter myAdapter=new myAdapter();
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    JSONObject jsonObject=jarray.getJSONObject(lostArr[i]);
                    String dtitle=jsonObject.getString("title");
                    String ditem=jsonObject.getString("item");
                    String dtype=jsonObject.getString("type");
                    String dtime=jsonObject.getString("time");
                    String dlocation=jsonObject.getString("location");
                    String ddescribe=jsonObject.getString("describe");

                    StoD.putExtra("title",dtitle);
                    StoD.putExtra("item",ditem);
                    StoD.putExtra("type",dtype);
                    StoD.putExtra("time",dtime);
                    StoD.putExtra("location",dlocation);
                    StoD.putExtra("describe",ddescribe);
                    StoD.putExtra("userid",userid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(StoD);            }
        });
    }
    public void ShowFOUND(View v){
        listView = (ListView) findViewById(R.id.postlist);//在视图中找到ListView
        Showtype="found";
        myAdapter myAdapter=new myAdapter();
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    JSONObject jsonObject=jarray.getJSONObject(foundArr[i]);
                    String dtitle=jsonObject.getString("title");
                    String ditem=jsonObject.getString("item");
                    String dtype=jsonObject.getString("type");
                    String dtime=jsonObject.getString("time");
                    String dlocation=jsonObject.getString("location");
                    String ddescribe=jsonObject.getString("describe");

                    StoD.putExtra("title",dtitle);
                    StoD.putExtra("item",ditem);
                    StoD.putExtra("type",dtype);
                    StoD.putExtra("time",dtime);
                    StoD.putExtra("location",dlocation);
                    StoD.putExtra("describe",ddescribe);
                    StoD.putExtra("userid",userid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(StoD);            }
        });
    }

    public void getJarray(String result) throws Exception {
        if(result==null)
            return;
        jarray=new JSONArray(result);
        Count_all=jarray.length();
        Count_lost=0;
        int l=0,f=0;
        for(int i=0;i<Count_all;i++){
            JSONObject jsonObject=jarray.getJSONObject(i);
            if(jsonObject.getString("type").equals("lost")){
                l++;
            }else{
                f++;
            }
        }
        lostArr=new int[l];
        foundArr=new int[f];
        l=0;
        f=0;
        for(int i=0;i<Count_all;i++){
            JSONObject jsonObject=jarray.getJSONObject(i);
            if(jsonObject.getString("type").equals("lost")){
                lostArr[l]=i;
                l++;
            }else{
                foundArr[f]=i;
                f++;
            }
        }
        Count_lost=lostArr.length;
        Count_found=foundArr.length;
    }

    public void search(View v){
        EditText e_keyword=(EditText)findViewById(R.id.search);
        keyword=e_keyword.getText().toString();
        Showtype="search";
        myAdapter myAdapter=new myAdapter();
        listView.setAdapter(myAdapter);
    }
    public void ME(View v){
        StoM.putExtra("userid",userid);
        startActivity(StoM);
    }

    private class myAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if(Showtype.equals("all")||Showtype.equals("search"))
            return Count_all;
        else if(Showtype.equals("lost"))
        return Count_lost;
        else
        return Count_found;}

        @Override
        public Object getItem(int position) { return null; }

        @Override
        public long getItemId(int position) { return 0; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v=getLayoutInflater().inflate(R.layout.listitem,null);
            String title=null;
            String content=null;
            TextView t_title=(TextView) v.findViewById(R.id.item_title);
            TextView t_item=(TextView) v.findViewById(R.id.item_content);
            if(Showtype.equals("all")){
                try {
                    JSONObject jobj=jarray.getJSONObject(position);
                    title="Title: "+jobj.getString("title");
                    content="Item: "+jobj.getString("item")+
                            "\nType: "+jobj.getString("type")+
                            "\nDescribe: "+jobj.getString("describe");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else if(Showtype.equals("lost")){
                try {
                    JSONObject jobj=jarray.getJSONObject(lostArr[position]);
                    if(jobj.getString("type").equals("lost")){
                    title="Title: "+jobj.getString("title");
                    content="Item: "+jobj.getString("item")+
                            "\nType: "+jobj.getString("type")+
                            "\nDescribe: "+jobj.getString("describe");}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if(Showtype.equals("found")){
                try {
                    JSONObject jobj=jarray.getJSONObject(foundArr[position]);
                    if(jobj.getString("type").equals("found")){
                        title="Title: "+jobj.getString("title");
                        content="Item: "+jobj.getString("item")+
                                "\nType: "+jobj.getString("type")+
                                "\nDescribe: "+jobj.getString("describe");}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if(Showtype.equals("search")){
                try {
                    JSONObject jobj=jarray.getJSONObject(position);
                        title="Title: "+jobj.getString("title");
                        content="Item: "+jobj.getString("item")+
                                "\nType: "+jobj.getString("type")+
                                "\nDescribe: "+jobj.getString("describe");
                        if(title.indexOf(keyword)==-1){
                            title=null;
                            content=null;
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
           if(title!=null&&content!=null){
            t_title.setText(title);
            t_item.setText(content);}
            return v;
        }
    }


}
