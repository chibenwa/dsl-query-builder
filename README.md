# DSL Query Builder

Note : This project is still in development and more stuff is coming.

## Why this project ?

I was using [Jest]([https://github.com/searchbox-io/Jest) for one of my project, as I had to query Elastic Search in Java. But as an other part of the programm I was working on was using an outdated Lucene version, I couldn't use Lucene's query builder.

To solve this problem, I decided to write my own query builder, with only a depandancy to Google Json library.

## How does it work ?

Using it is very simple... First have a look to [queries]() and [filters]() to know what kind of query you can perform with Elastic Search. Names used in this project are very similar, so finding the stuff you need should not be too complicated.

You have two basic objects :

  - a **DSLQuery** that represents a query you can perform with elastic search. Each query that can be built with this project implements this interface. The basic operation you can perform with DSLQueries is to get them as JSON.
  - You have the same thing with **DSLFilter**.
  - You can combine a query and filter using the **DSLQueryBuilder**. This object is here to make the creation of a query easier : if you have only a filter, the JSON generated will be a filtered match all query, if it is a query with no filter it will return the JSON of your query, and if you decide to combine a query with a filter, it will create a filtered query.

### Examples

#### Using Jest

Using the DSL query builder with Jest is very simple. If you have initialized a Jest Client, you just have to do :

```java
JestClient jestClient;
String searchQueryString = new DSLBuilder()
                .setQuery(new DSLMatchAllQuery())
                .setFilter(new DSLTermFilter("user").stringFilter("kimchi"))
                .build()
                .toString();
Search search = new Search.Builder(searchQueryString)
                .addIndex("twitter")
                .addType("tweet")
                .build();
SearchResult searchResult = jestClient.execute();
```

This will perform the following operation :

```
curl -XPOST 'http://your.elastic.search.cluster/twitter/tweet/_search' -d '
{
	"query" : {
    	"filtered" : {
        	"query" : {
            	"match_all" : {}
            },
            "filter" : {
            	"term" : {"user" : "kimchi"}
            }
        }
    }
}'
```

#### Other items

Here comes a non exhaustive list of what you can do with the DSL Query Builder.

  - DSLMatchAllQuery

```java
System.out.println(new DSLMatchAllQuery().getQueryAsJson());
```

Would give :

```
{
	"match_all" : {}
}
```

  - DSLMatchQuery

```java
System.out.println(new DSLMatchQuery("user", "kimchi").getQueryAsJson());
```

Would give :

```
{
	"match" : {"user":"kimchi"}
}
```

  - DSLBoolQuery

```java

System.out.println(new DSLBoolQuery()
						.must(new DSLMatchQuery("user", "kimchi"))
                        .must(new DSLMatchQuery("article", "travel"))
                        .should(new DSLMatchQuery("location, "Paris"))
                        .should(new DSLMatchQuery("location", "London"))
                        .must_not(new DSLMatchQuery("media", "Television"))
						.getQueryAsJson()
);
```

Would give :

```
{
	"bool" : {
    	"must" : [
        	{
            	"match" : {"user":"kimchi"}
            },
            {
            	"match" : {"article":"travel"}
            }
        ],
        "should" : [
        	{
            	"match" : {"location":"Paris"}
            },
            {
            	"match" : {"location":"London"}
            }
        ],
        "must_not" : [
        	{
            	"match" : {"media":"Television"}
            }
        ]
    }
}
```

  - DSLRangeFilter

```java
System.out.println(new DSLRangeFilter("date").gte("2014/05/12"));
```

would give :

```
{
	"range" : {
    	"date" : {
        	"gte" : "2014/05/12"
        }
    }
}
```

and :

```java
System.out.println(new DSLRangeFilter("date").gte(2048));
```

would give :

```
{
	"range" : {
    	"date" : {
        	"gte" : 2048
        }
    }
}
```

## What to come ?

I will implement each DSL operation you can perform with Elastic Search. Just give me one or two mounths to write it down...
