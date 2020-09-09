package com.zzw.common.core.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.List;

/**
 * create by zhangzhiwen on 2020/8/11
 */
public class HtmlUtils{
    private static String extractText(Node node){
        /* TextNode直接返回结果 */
        if(node instanceof TextNode){
            return ((TextNode) node).text();
        }
        /* 非TextNode的Node，遍历其孩子Node */
        List<Node> children = node.childNodes();
        StringBuffer buffer = new StringBuffer();
        for (Node child: children) {
            buffer.append(extractText(child));
        }
        return buffer.toString();
    }
    /* 使用jsoup解析html并转化为提取字符串*/
    public static String html2Str(String html){
        Document doc = Jsoup.parse(html);
        return extractText(doc);
    }

    public static void main(String[] args) {
        System.out.println(HtmlUtils.html2Str("<h3>查看已有镜像</h3>\\n<pre><code class=\\\"lang-\\\">//查看rabbitmq已经存在镜像，management表示支持web可视化\\ndocker search rabbitmq:management\\n</code></pre>\\n<p><strong>结果如下</strong>：</p>\\n<pre><code class=\\\"lang-\\\">[root@mail ~]# docker search rabbitmq:management\\nNAME                                DESCRIPTION                                     STARS               OFFICIAL            AUTOMATED\\nmacintoshplus/rabbitmq-management   Based on rabbitmq:management whit python and…   6                                       [OK]\\nxiaochunping/rabbitmq               xiaochunping/rabbitmq:management   2018-06-30   4                                       \\ntransmitsms/rabbitmq-sharded        Fork of rabbitmq:management with sharded_exc…   0 \\n</code></pre>\\n<h3>拉取镜像</h3>\\n<pre><code class=\\\"lang-\\\">//拉取镜像\\ndocker pull rabbitmq:management\\n</code></pre>\\n<p><strong>结果如下</strong>：</p>\\n<pre><code class=\\\"lang-\\\">[root@mail ~] docker pull rabbitmq:management\\nmanagement: Pulling from library/rabbitmq\\n5667fdb72017: Pull complete \\nd83811f270d5: Pull complete \\nee671aafb583: Pull complete \\n7fc152dfb3a6: Pull complete \\n511da93b5ba5: Pull complete \\n2e439885870f: Pull complete \\n5c07a284c0d9: Pull complete \\n0e4528af7d06: Pull complete \\nf8a8a551f015: Pull complete \\n5a7a6d68d51f: Pull complete \\n2210e947fea4: Pull complete \\nf0ea315cdd14: Pull complete \\nDigest: sha256:ff92870e678bbf18868a4da3da7a00048da04504cd34d8848d70bd2c5f64c9e9\\nStatus: Downloaded newer image for rabbitmq:management\\ndocker.io/library/rabbitmq:management\\n</code></pre>\\n<p>查看已有镜像，如下：</p>\\n<pre><code class=\\\"lang-\\\">[root@mail ~]# docker images\\nREPOSITORY                                      TAG                 IMAGE ID            CREATED             SIZE\\nrabbitmq                                        management          d55229deb03e        3 days ago          187MB\\n</code></pre>\\n<h3>启动镜像</h3>\\n<pre><code class=\\\"lang-\\\">//默认启动，账户密码默认都是guest\\ndocker run -d -p 5672:5672 -p 15672:15672 --name rabbitmq rabbitmq:management\\n</code></pre>\\n<pre><code class=\\\"lang-\\\">//账号user 密码111111\\ndocker run -d --hostname rabbitmq --name rabbitmq -e RABBITMQ_DEFAULT_USER=user -e RABBITMQ_DEFAULT_PASS=111111 -p 15672:15672 -p 5672:5672 rabbitmq:management\\n</code></pre>\\n<p>http://[宿主机IP]:15672，即可访问mq页面！</p>\\n<p>部署完成。</p>\\n"));
    }
}
