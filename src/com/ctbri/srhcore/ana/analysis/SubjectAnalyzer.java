package com.ctbri.srhcore.ana.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import com.ctbri.srhcore.C;
import com.ctbri.srhcore.U;
import com.ctbri.srhcore.ana.LocalQuery;
import com.ctbri.srhcore.ana.ele.Subject;


/**
 * �����ַ������Ƿ����Subject��Ϣ������data\���� �ļ��µ��ļ���<br>
 * ��lq��Ӱ�� hasBusStationType tokenEntities type_list
 * @author baduo  Email:zhoujc@mapbar.com
 *
 */
public class SubjectAnalyzer extends Analyzer{
	
	public SubjectAnalyzer()
	{
//		checkSubjectDic();
		this.order = 70;
	}
	public SubjectAnalyzer(int order)
	{
//		checkSubjectDic();
		this.order = order;
	}
	
	public String parse(LocalQuery lq,String key)
	{
		int startIndex = 0;
		String[] keys = key.split(" ");
		for (int i = 0; i < keys.length; i++) {
			if(SUBJECT_DICTIONARY.containsKey(keys[i]))
			{
				Subject s = new Subject();
				s.setType((String)SUBJECT_DICTIONARY.get(keys[i]));
				s.setKey(keys[i]);
				
				lq.setType(s.getType().toLowerCase());
				s.setSubject(keys[i]);		
				
				int index = lq.getSegStrIndex(keys[i],s.getOrder());
				if(index >-1)
					lq.getTokenEntities()[index]  = s;
				key = key.substring(0, startIndex)+ key.substring(startIndex).replaceFirst(keys[i], "");
                startIndex = startIndex - keys[i].length();
				lq.getType_list().add(s);
//				key = key.replaceFirst(keys[i], "");
			}
			startIndex += keys[i].length()+1;
		}
		key = key.replaceAll(" +", " ").trim();
		U.log.info(key);
		return key;
	}
	
	private static HashMap<String,String> SUBJECT_DICTIONARY = new HashMap<String,String>();
	static
	{
		try
		{
			String dicDir = C.DATA_PATH + File.separator + C.Subject_dic 
				+ File.separator + "subjecttype.dic";
			U.log.info("�����ļ� "+dicDir);
			BufferedReader br = new BufferedReader(new FileReader(dicDir));

            for(String s2 = null; (s2 = br.readLine()) != null;) {
                s2 = s2.trim();
                if(s2 != null && s2.length() > 0)
                {
                    int idx = s2.indexOf(":");
                    if(idx > 0)
                    {
                        String typeName = s2.substring(0,idx).trim();
                        String[] Subject = s2.substring(idx+1).trim().split(",");
                        for(int i=0; i<Subject.length; i++) {
                            Subject[i] = Subject[i].trim();
                            if(Subject[i].length()>0) {
                            	SUBJECT_DICTIONARY.put(Subject[i], typeName);
                            }
                        }
                    }
                }
            }
            br.close();
		}
		catch(Exception e)
		{
			U.log.error(e);
		}
	}

	public static Subject parse(String xQuery) {
//      Subject subject = new Subject();
   //   ArrayList subject = new ArrayList();
		
		if(SUBJECT_DICTIONARY == null || SUBJECT_DICTIONARY.keySet().size() == 0)
			U.log.error("�����ֵ�û���ļ��������Ƿ��������ļ���������·���Ƿ���ȷ����");
		
		if(SUBJECT_DICTIONARY.containsKey(xQuery))
		{
			Subject s = new Subject();
			s.setType((String)SUBJECT_DICTIONARY.get(xQuery));
			s.setSubject(xQuery);
			s.setKey(xQuery);
			return s;
		}
		else
		{
//			U.log.error("û�з����������:"+ xQuery);
		}
		
		return null;	      
	}
	
	public static void main(String[] args) {
		Subject s = SubjectAnalyzer.parse("�˶�����");
		System.out.println(Subject .getTypeCode("600"));;
		System.out.println(s.getType());
	}
}
