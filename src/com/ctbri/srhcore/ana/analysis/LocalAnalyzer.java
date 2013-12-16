package com.ctbri.srhcore.ana.analysis;


import com.ctbri.base.util.ChineseCoding;
import com.ctbri.srhcore.C;
import com.ctbri.srhcore.U;
import com.ctbri.srhcore.ana.LocalQuery;
import com.ctbri.srhcore.ana.analysis.ctbri.CtbriAnalyzer.TokenizerModel;



public class LocalAnalyzer {
	
	private Analyzer[] anas = {

			new CityAnalyzer(),
			new BrandAnalyzer(),

//			new TagKeywordAnalyzer2(23),
//			new SubjectAnalyzer(24),//���������һ��������������������order<30���ڷִ�֮ǰ,			
//			new AddressAnalyzer(26),//
//			new SameKeywordAnalyzer(27),
//
//			new XKeywordAnalyzer(),//30
//			new SameKeywordAnalyzer(),
//			new XtypeKeywordAnalyzer(),
//
			new SubjectAnalyzer(), 
//			new DistrictAnalyzer(),
//			new LocationAnalyzer()
			
	};
	
	private int MAX_TYPE = (int)Math.pow(2, anas.length)-1;
	private int type;
	
	public  LocalAnalyzer()
	{
		type = MAX_TYPE;
	}	
	

	/**��λ���õĹ��������������typeֵ�����ε��ø��������������н���
	 * @param strQuery �û�������
	 * @return LocalQuery �����Ľ��
	 */
	public LocalQuery parse(String strQuery)
	{
		LocalQuery lq = new LocalQuery(strQuery);
		U.fileLog.info(strQuery.replaceFirst(" ", ","));
		if(this.type == 0)
		{
			U.log.info("��û��ָ���κη�����");
			return lq;
		}
		String tempQuery = ChineseCoding.DBC2SBC(ChineseCoding.TraditionalToSimplify(strQuery)).toLowerCase();//cjk�ַ�����
		
		boolean isSeg = false;
		String binStr = Integer.toBinaryString(type);
		for (int i = binStr.length()-1; i >=0; i--) {
			if(binStr.charAt(i) == '1' && tempQuery != null && tempQuery.length()>0)
			{				
				if(!isSeg && anas[binStr.length()-1 -i].order >= 30 //���÷ִ�֮ǰ���߼�
				)
				{
					String te = tempQuery.trim().replaceAll(" +", " ");
					
					if(te != null && te.length()>0)
					{
						lq.noSpaceStr = te;
						lq.noSpace = te.split(" ");
					}
					isSeg = true;
					String segStr = WordAnalyzer.analyzer(tempQuery,C.segMode);//TokenizerModel.SEG2); //�ִ�
					if(segStr == null || segStr.length()== 0) return lq;					
					lq.setSegStr(segStr);
					tempQuery = segStr;
				}
				U.log.info(anas[binStr.length()-1 -i].getClass().toString() + "  "+(int)Math.pow(2, (binStr.length()-1-i)));
				
				
				tempQuery = anas[binStr.length()-1 -i].parse(lq, tempQuery);
			}			
		}			
		tempQuery = new OtherKeywordAnalyzer().parse(lq,tempQuery);//0 ������������
		
		lq.statKeywords();
		return lq;
	}
	
	public boolean isContainAnalyzer(String className)
	{
		for (int i = 0; i < this.anas.length; i++) {
			if(this.anas[i].getClass().toString().equals(className))
			{
				return true;
			}
		}
		return false;
	}

}
