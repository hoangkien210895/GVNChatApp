/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatgvn.utils;

/**
 *
 * @author Phan Huy
 */
public class ApiProject {

    public static String API_LOGIN_POST = "http://10.64.100.23:4567/login";
    public static String API_LOGOUT_POST = "http://10.64.100.23:4567/account/logout";
    public static String API_REGISTER_POST = "http://10.64.100.23:4567/createuser";
    public static String API_CHECK_USERNAME_POST = "http://10.64.100.23:4567/account/getuser/";
    public static String API_SET_USERNAME_POST = "http://10.64.100.23:4567/account/setusername";
    public static String API_ChangPass_POST = "http://10.64.100.23:4567/changepassword";
    public static String API_CheckOnline_POST = "http://10.64.100.23:4567/account/getuseronline";
    public static String API_CheckGroup_POST = "http://10.64.100.23:4567/group/getaccepted/";
    public static String API_CheckRequest_POST = "http://10.64.100.23:4567/group/getlistrequest/";
    public static String API_LoadLogChat_POST = "http://10.64.100.23:4567/group/get";
    public static String API_requestchat_POST = "http://10.64.100.23:4567/group/requestchat";
    public static String API_AccepRequest_POST = "http://10.64.100.23:4567/group/acceptrequest";
    public static String API_AddGroup_POST = "http://10.64.100.23:4567/group/addtogroup";
    public static String API_LeftGroup_POST = "http://10.64.100.23:4567/group/leftgroup";
    
    
    //http://localhost:4567/group/addtogroup
    //"http://10.64.100.23:4567/login";
}
