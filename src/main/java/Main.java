
import io.github.ccincharge.newsapi.datamodels.Article;

import network.myNewsApi;
import tool.MyTool;
import tool.myLoadMysql;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    final static int Max_T=10;
    public static void main(String[] args) {

        myNewsApi myNewsApi=new myNewsApi();
        myLoadMysql loadMysql=new myLoadMysql();
        Statement stmt= loadMysql.LoadMysql("changsan");
        getNewsFromWeb web;

        ExecutorService pool = Executors.newFixedThreadPool(Max_T);
        for(int i=1;i<16;i++){
            web=new getNewsFromWeb(loadMysql,myNewsApi,i);
            pool.submit(new Thread(web));
        }
        pool.shutdown();

    }

    private static class  getNewsFromWeb implements Runnable{
        private myLoadMysql loadMysql;
        private myNewsApi NewsApi;
        private int page;

       public getNewsFromWeb(myLoadMysql arg1,myNewsApi arg2,int arg3){

           this.loadMysql=arg1;
           this.NewsApi=arg2;
           this.page=arg3;
       }

        @Override
        public void run() {
            loadMysql.setSQL("INSERT INTO store_url_info(title,hash_title,url,w_status) values (?,?,?,?)");

            ArrayList<String> list=loadMysql.get_all_articles("store_url_info");

            ArrayList<Article> articles= NewsApi.get_Articles("Julian Assange","en",page);

            articles.iterator().forEachRemaining(article->{

                String title=article.title();
                String hash_title=MyTool.ChineseToH(title);

                if (list.contains(hash_title)) return;

                int status=loadMysql.executeSQL(title,MyTool.ChineseToH(title),article.url());
                if (status==1){
                    list.add(MyTool.ChineseToH(title));
                    System.out.println("insert "+title+"to store_url_info OK!");
                }else{
                    System.out.println("insert failed,please check your code!");
                }
            });
            System.out.println(page+"Done!");
         }

    }


}
