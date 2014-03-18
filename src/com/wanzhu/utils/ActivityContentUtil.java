package com.wanzhu.utils;
//package com.wanzhu.utils;
//
//import com.wanzhu.base.CommonConstant;
//
///**
// * 动态内容组织类
// * 
// * @author ZOUSY
// */
//public class ActivityContentUtil
//{
//
//    private static final String URL = "<a href=\"#\">?</a>";
//    //用户详情
//    private static final String USER_DETAIL_CONTROLLER  = "user/friendInfo?userid=";
//    //话题详情
//    private static final String TOPIC_DETIAL_CONTROLLER  = "event/";
//    //活动详情
//    private static final String EVENT_DETAIL_CONTROLLER = "event/";
//    
//    /**
//     * 组织个人动态内容
//     * 
//     * @param type
//     *                      动态类型
//     * @param contentTxt
//     *                      纯文本内容：如
//     * @param targetId 
//     *                      某个活动/用户的id，依type而异。注意话题类型，targetId传活动id！
//     * @return
//     */
//    public static  String generatorContentOfPerson(int type,String contentTxt,String targetId)
//    {
//        StringBuffer result = new StringBuffer();
//        //赞同了话题[?]
//        if(type==CommonConstant.PERSONALACTIVITY_AGREE_TOPIC_TYPE)
//        {
//            result.append("赞同了话题").append(generatorContent(type, contentTxt, targetId));
//        }
//        //报名了[活动?]
//        if(type==CommonConstant.PERSONALACTIVITY_EVENT_TYPE)
//        {
//            result.append("报名了").append(generatorContent(type, contentTxt, targetId));
//        }
//        //和?成为好友
//        if(type==CommonConstant.PERSONALACTIVITY_FRIENDRELATIONSHIP_TYPE)
//        {
//            result.append("和").append(generatorContent(type, contentTxt, targetId)).append("成为好友");
//        }
//        //参与了话题[?]
//        if(type==CommonConstant.PERSONALACTIVITY_PARTICIPATE_TOPIC_TYPE)
//        {
//            result.append("参与了话题").append(generatorContent(type, contentTxt, targetId));
//        }
//        //发表了话题[?]
//        if(type==CommonConstant.PERSONALACTIVITY_PUBlLISHIED_TOPIC_TYPE)
//        {
//            result.append("发表了话题").append(generatorContent(type, contentTxt, targetId));
//        }
//        return result.toString();
//    }
//    
//    /**
//     * 组织好友动态内容
//     * 
//     * @param type
//     *                      动态类型
//     * @param contentTxt
//     *                      纯文本内容：如，怎么应对大数据处理?
//     *  @param targetId
//     *                      某个活动/用户的id，依type而异。注意话题类型，targetId传活动id！
//     *                      
//     * @return    组织后的动态内容：如，
//     *                                          发表了话题<a href="#">怎么应对大数据处理?</a>
//     */
//    public static  String generatorContentOfFriend(int type,String contentTxt,String targetId)
//    {
//        StringBuffer result = new StringBuffer();
//        //报名参加了[活动?]
//        if(type==CommonConstant.FRIENDACTIVITY_EVENT_TYPE)
//        {
//            result.append("报名参加了");
//        }
//        //参与了话题[?]
//        if(type==CommonConstant.FRIENDACTIVITY_PARTICIPATE_TOPIC_TYPE)
//        {
//            result.append("参与了话题");
//        }
//        //发表了话题[?]
//        if(type==CommonConstant.FRIENDACTIVITY_PUBlLISHIED_TOPIC_TYPE)
//        {
//            result.append("发表了话题");
//        }
//        result.append(generatorContent(type,contentTxt, targetId));
//        return result.toString();
//    }
//    
//    //组织<a href="...">...</a>
//    private static  String generatorContent(int type,String contentTxt,String targetId)
//    {
//       String result = URL.replaceAll("\\?", contentTxt);
//       //话题
//       if(type==CommonConstant.FRIENDACTIVITY_PARTICIPATE_TOPIC_TYPE 
//               ||type==CommonConstant.FRIENDACTIVITY_PUBlLISHIED_TOPIC_TYPE
//               ||type==CommonConstant.PERSONALACTIVITY_AGREE_TOPIC_TYPE
//               ||type==CommonConstant.PERSONALACTIVITY_PARTICIPATE_TOPIC_TYPE
//               ||type==CommonConstant.PERSONALACTIVITY_PUBlLISHIED_TOPIC_TYPE)
//       {
//           result=result.replaceAll("#",TOPIC_DETIAL_CONTROLLER+targetId+".html#activityRemark");
//       }
//       //活动
//       if(type==CommonConstant.FRIENDACTIVITY_EVENT_TYPE
//               || type==CommonConstant.PERSONALACTIVITY_EVENT_TYPE)
//       {
//           result=result.replaceAll("#",EVENT_DETAIL_CONTROLLER+targetId+".html");
//       }
//       //用户
//       if(type==CommonConstant.PERSONALACTIVITY_FRIENDRELATIONSHIP_TYPE)
//       {
//           result=result.replaceAll("#",USER_DETAIL_CONTROLLER+targetId);
//       }
//        return result;
//    }
//    
//    public static void main(String[] args)
//    {
//        System.out.println(generatorContentOfFriend(1, "哈哈哈", "4444"));
//        
//        System.out.println(generatorContentOfPerson(4, "张三", "343214828409234809"));
//    }
//}
