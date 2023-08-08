package agent.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class testMain {
    public static void main(String[] args) {
        List<String> dataList = new ArrayList<>();

        dataList.add("AP_IDE_BAT_RUN_INFO");
        dataList.add("AP_IDE_CO");
        dataList.add("AP_IDE_COMN_CD");
        dataList.add("AP_IDE_JOB_PROJ");
        dataList.add("AP_IDE_PROJ");
        dataList.add("AP_IDE_SYS");
        dataList.add("BOARD");
        dataList.add("CUSTOMER");
        dataList.add("EIMS_BODY");
        dataList.add("EIMS_HEAD");
        dataList.add("EIMS_INF");
        dataList.add("EIMS_LOG");
        dataList.add("FW_ACCESS_CONTROL_RULES");
        dataList.add("FW_APPL");
        dataList.add("FW_APPL_DATASOURCE");
        dataList.add("FW_CACHE_HISTORY");
        dataList.add("FW_CROSS_DOMAIN");
        dataList.add("FW_DATASOURCE");
        dataList.add("FW_IDE_OPTION");
        dataList.add("FW_IDE_TEMPLATE_ANNOTATION");
        dataList.add("FW_IDE_TEMPLATE_ANNOTATION_DETAIL");
        dataList.add("FW_IDE_TEMPLATE_BATCH");
        dataList.add("FW_IDE_TEMPLATE_COMMENT");
        dataList.add("FW_IDE_TEMPLATE_EXTENDS");
        dataList.add("FW_IDE_TEMPLATE_IMPLEMENTS");
        dataList.add("FW_IDE_TEMPLATE_IMPORT");
        dataList.add("FW_IDE_TEMPLATE_MAIN");
        dataList.add("FW_IDE_TEMPLATE_METHOD");
        dataList.add("FW_IDE_TEMPLATE_METHOD_ANNOTATION_LINK");
        dataList.add("FW_IDE_TEMPLATE_METHOD_PARAM_LINK");
        dataList.add("FW_IDE_TEMPLATE_VARIABLE");
        dataList.add("FW_IDE_TEMPLATE_VARIABLE_ANNOTATION_LINK");
        dataList.add("FW_INTERFACE");
        dataList.add("FW_JUNMUN_DETAIL");
        dataList.add("FW_JUNMUN_INFO");
        dataList.add("FW_MESSAGE");
        dataList.add("FW_META");
        dataList.add("FW_METALINK");
        dataList.add("FW_MYBATIS_INFO");
        dataList.add("FW_ONLINE_LOG");
        dataList.add("FW_PST_INQ");
        dataList.add("FW_QA_BBS");
        dataList.add("FW_SCRIPT");
        dataList.add("FW_SERVLET");
        dataList.add("FW_SERVLET_METHOD");
        dataList.add("FW_SITE_URL");
        dataList.add("FW_SQL_LOG");
        dataList.add("FW_SYSTEM");
        dataList.add("FW_SYSTEM_LINK");
        dataList.add("FW_USER");
        dataList.add("FW_USER_APPL");
        dataList.add("FW_USER_LOG");
        dataList.add("TB_BAT_APSVR_BATPRCT");
        dataList.add("TB_BAT_HOST");
        dataList.add("TB_BAT_HOST_ENV");
        dataList.add("TB_BAT_JOB");
        dataList.add("TB_BAT_JOB_EXECUTION");
        dataList.add("TB_BAT_JOB_EXECUTION_CONTEXT");
        dataList.add("TB_BAT_JOB_EXECUTION_PARAMS");
        dataList.add("TB_BAT_JOB_INSTANCE");
        dataList.add("TB_BAT_JOB_PARM_RULE");
        dataList.add("TB_BAT_JOB_RUN_NOTUSE");
        dataList.add("TB_BAT_STEP_EXECUTION");
        dataList.add("TB_BAT_STEP_EXECUTION_CONTEXT");
        dataList.add("TB_RSC");
        dataList.add("TC_CODE");
        dataList.add("TC_CODE_CHOICE");
        dataList.add("TH_CODE");
        dataList.add("TH_CODE_CHOICE");
        dataList.add("TH_FILE_DWLD");
        dataList.add("TH_MNGR_CHANGE");
        dataList.add("TH_MNGR_CONECT");
        dataList.add("TH_PRHIBT_WRD");
        dataList.add("TH_USER_CONECT");
        dataList.add("TH_USER_INFO_IEM_ESTBS");
        dataList.add("TH_USER_LOG");
        dataList.add("TH_USER_MENU_LAYOUT");
        dataList.add("TN_ACCES_CTRL");
        dataList.add("TN_AUTHOR");
        dataList.add("TN_BANNER");
        dataList.add("TN_BANNER_DOMN");
        dataList.add("TN_BBS");
        dataList.add("TN_BBS_CMNT");
        dataList.add("TN_BBS_CTGRY");
        dataList.add("TN_BBS_DOMN");
        dataList.add("TN_BBS_FORM_SETUP");
        dataList.add("TN_BBS_GLOBAL_SETUP");
        dataList.add("TN_BBS_ITEM_SETUP");
        dataList.add("TN_BBS_LIST_SETUP");
        dataList.add("TN_BBS_LWPRT_CTGRY");
        dataList.add("TN_BBS_MAIN_SETUP");
        dataList.add("TN_BBS_SETUP");
        dataList.add("TN_BBS_TAG");
        dataList.add("TN_BBS_TMPLAT");
        dataList.add("TN_BBS_VIEW_SETUP");
        dataList.add("TN_DEPT");
        dataList.add("TN_DOMN");
        dataList.add("TN_DOMN_GROUP");
        dataList.add("TN_FILE");
        dataList.add("TN_HPCM");
        dataList.add("TN_MENU");
        dataList.add("TN_MENU_AUTHOR_ASGN");
        dataList.add("TN_MENU_URL");
        dataList.add("TN_MNGR");
        dataList.add("TN_MNGR_AUTHOR_ASGN");
        dataList.add("TN_MSSAGE");
        dataList.add("TN_MULTI_LANG");
        dataList.add("TN_MULTI_LANG_GROUP");
        dataList.add("TN_MY_MENU");
        dataList.add("TN_POPUP");
        dataList.add("TN_POPUP_DOMN");
        dataList.add("TN_PRHIBT_WRD");
        dataList.add("TN_RESRCE");
        dataList.add("TN_RESRCE_CNTNTS");
        dataList.add("TN_RESRCE_CTGRY");
        dataList.add("TN_RESRCE_FILE");
        dataList.add("TN_RESRCE_OTHBC");
        dataList.add("TN_SCHDUL");
        dataList.add("TN_SCHDUL_DETAIL");
        dataList.add("TN_USER");
        dataList.add("TN_USER_ENTRPRS_ETC_INFO");
        dataList.add("TN_USER_ETC_INFO");
        dataList.add("TN_USER_GRAD");
        dataList.add("TN_USER_INFO_IEM_ESTBS");
        dataList.add("TN_USER_INTRST_IEM_ESTBS");
        dataList.add("TN_USER_MENU");
        dataList.add("TN_USER_MENU_AUTHOR");
        dataList.add("TN_USER_MENU_AUTHOR_ASGN");
        dataList.add("TN_USER_MENU_CNTNTS");
        dataList.add("TN_USER_MENU_EVL");
        dataList.add("TN_USER_MENU_EVL_DETAIL");
        dataList.add("TN_USER_MENU_GRAD");
        dataList.add("TN_USER_MENU_LAYOUT");
        dataList.add("TN_USER_MENU_LWPRT_URL");
        dataList.add("TN_USER_MENU_META");
        dataList.add("TSTCO001M");
        dataList.add("TSTCO002M");
        dataList.add("TS_USER_CONECT");

        check(dataList);

    }
    
    public static void check(List<String> dataList) {
        // 파일 경로 설정
        String filePath = "C:\\Users\\DEV ycjung\\Desktop\\TestFile\\FrameXpert_Oracle_DDL(2023.08.08).sql";

        // dataList의 각 String에 대해 모든 줄에서 해당 String이 존재하는지 체크
        for (String data : dataList) {
            boolean foundInAllLines = false;  // 해당 String이 모든 줄에 존재하는지 여부
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;

                // 파일을 한 줄씩 읽음
                while ((line = reader.readLine()) != null) {
                    if (line.contains(data)) {
                        foundInAllLines = true; // 라인에 있다..
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 해당 String이 모든 줄에 존재하지 않는 경우 출력
            if (!foundInAllLines) {
                System.out.println("String '" + data + "' is missing in at least one line.");
            }
        }
    }
}
