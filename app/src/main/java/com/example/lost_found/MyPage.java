package com.example.lost_found;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyPage extends AppCompatActivity {
    EditText ename,ecollege,emajor;
    Intent toManager;
    String name;
    String college;
    String major;
    Intent getStoM,MtoA;
    boolean flag;
    String userid,result;
    JSONArray jarray;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        getStoM=getIntent();
        MtoA=new Intent(this,AddPost.class);
        toManager=new Intent(this,ManagePost.class);
        ename=(EditText)findViewById(R.id.name);
        ecollege=(EditText)findViewById(R.id.college);
        emajor=(EditText)findViewById(R.id.major);

        flag=false;
        userid=getStoM.getStringExtra("userid");

        try {
            findInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ename.setText(name);
        ecollege.setText(college);
        emajor.setText(major);
        ename.setEnabled(false);
        ecollege.setEnabled(false);
        emajor.setEnabled(false);

        try {
            showMypost();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void EDIT(View v){
        flag=true;
        ename.setEnabled(true);
        ecollege.setEnabled(true);
        emajor.setEnabled(true);
    }

    public void SAVE(View v) throws Exception {
        if(flag){
            String name=ename.getText().toString();
            String college=ecollege.getText().toString();
            String major=emajor.getText().toString();
            String url="http://192.168.0.103:8080/edituser.php";
            String resultinfo=null;
            JSONObject jsonobj=new JSONObject();
            jsonobj.put("userid",userid);
            jsonobj.put("name",name);
            jsonobj.put("college",college);
            jsonobj.put("major",major);
            jsonobj.put("action","EDIT");
            resultinfo=MyThread.Show(url,jsonobj,this,result);
            ename.setEnabled(false);
            ecollege.setEnabled(false);
            emajor.setEnabled(false);
        }
        flag=false;
    }


    public void showMypost() throws Exception {
        String url="http://192.168.0.103:8080/findME.php";
        result=null;
        JSONObject jsonobj=new JSONObject();
        jsonobj.put("userid",userid);
        jsonobj.put("action","getmypost");
        result=MyThread.Show(url,jsonobj,this,result);
        /*Toast.makeText(this, result, Toast.LENGTH_SHORT).show();*/
        if(!result.equals("no data")){
            result=result.substring(0,result.length()-1);
            result+="]";
        }else{
            result=null;
        }
        System.out.println("我发表的的帖子有"+result);
        if(result==null)
            return;
        jarray=new JSONArray(result);

        listView = (ListView) findViewById(R.id.mypostlist);//在视图中找到ListView
        MyPage.MyAdapter myAdapter=new MyAdapter();
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

                    toManager.putExtra("title",dtitle);
                    toManager.putExtra("item",ditem);
                    toManager.putExtra("type",dtype);
                    toManager.putExtra("time",dtime);
                    toManager.putExtra("location",dlocation);
                    toManager.putExtra("describe",ddescribe);
                    toManager.putExtra("userid",userid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(toManager);            }
        });

    }
    public void findInfo() throws Exception {
        String url="http://192.168.0.103:8080/findME.php";
        String result=null;
        JSONObject jsonobj=new JSONObject();
        jsonobj.put("userid",userid);
        jsonobj.put("action","findinfo");
        result=MyThread.Show(url,jsonobj,this,result);
        System.out.println(result);
        JSONObject jsonObject=new JSONObject(result);
        name=jsonObject.getString("name");
        college=jsonObject.getString("college");
        major=jsonObject.getString("major");
    }
    public void ADD(View V){
        MtoA.putExtra("userid",userid);
        startActivity(MtoA);
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return jarray.length();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v=getLayoutInflater().inflate(R.layout.listitem,null);
            String title=null;
            String content=null;
            TextView t_title=(TextView) v.findViewById(R.id.item_title);
            TextView t_item=(TextView) v.findViewById(R.id.item_content);
            try {
                JSONObject jobj=jarray.getJSONObject(position);
                title="Title: "+jobj.getString("title");
                content="Item: "+jobj.getString("item")+
                        "\nType: "+jobj.getString("type")+
                        "\nDescribe: "+jobj.getString("describe");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(title!=null&&content!=null){
                t_title.setText(title);
                t_item.setText(content);}
            return v;
        }
    }
}
