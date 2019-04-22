package network;

import io.github.ccincharge.newsapi.NewsApi;
import io.github.ccincharge.newsapi.datamodels.Article;
import io.github.ccincharge.newsapi.requests.RequestBuilder;
import io.github.ccincharge.newsapi.responses.ApiArticlesResponse;

import java.util.ArrayList;

public class myNewsApi {

    final static String API_KEY="News_API KEY";

    private  NewsApi newsApi;

    public myNewsApi(){
        newsApi=new NewsApi(API_KEY);
    }

    public  ArrayList<Article> get_Articles(String Q,String language,int page){

        RequestBuilder techRequest=new RequestBuilder()
                .setQ(Q)
                .setLanguage(language)
                .setPage(page);

        ApiArticlesResponse apiArticlesResponse=newsApi.sendEverythingRequest(techRequest);

        String responseStatus=apiArticlesResponse.status();
        if (!responseStatus.equals("ok")){
            return null;
        }
        return apiArticlesResponse.articles();
    }
}
