package com.ctbri.srhcore.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Iterator;
/**
 * ��ϵͳ��ֱ������
 *
 */
public class WordToNumber
{
  private static Hashtable nUnit_Simp;
  private static Hashtable nUnit_All;
  private static Hashtable NumberStr_Simp = new Hashtable();
  private static Hashtable NumberStr_All;
  


  public static String operate(String keyWord)
  {
    return operate(keyWord, 1);
  }

  public static String operate(String keyWord, int mode)
  {
    Hashtable nUnit = new Hashtable();
    Hashtable NumberStr = new Hashtable();
    Hashtable tempNumberArray = new Hashtable();

    if (mode == 0) {
      nUnit = nUnit_Simp;
      NumberStr = NumberStr_Simp;
    } else {
      nUnit = nUnit_All;
      NumberStr = NumberStr_All;
    }

    String tempWord = "";
    String Word = keyWord;

    boolean numSign = false;
    boolean unitSign = false;
    String tempNumber = "";

    int lastUnitLevel = 0;

    for (int i = 0; i < Word.length(); ++i) {
      String tmpStr = Word.substring(i, i + 1);

      if ((NumberStr.containsKey(tmpStr)) || ((numSign) && (nUnit.containsKey(tmpStr))))
      {
        if ((tmpStr.equals("ʮ")) || (tmpStr.equals("ʰ")))
        {
          if (tempNumber.length() > 0)
          {
            tempNumber = tempNumber + nUnit.get(tmpStr);
          }
          else
            tempNumber = tempNumber + NumberStr.get(tmpStr);

          lastUnitLevel = 1;
          unitSign = true;
        }
        else if (NumberStr.containsKey(tmpStr))
        {
          if ((tempNumber.length() < 1) || (!(unitSign))) {
            tempNumber = tempNumber + "0";
          }

          boolean tmpSign = true;
          if ((unitSign) && 
            (!(NumberStr.get(tmpStr).toString().equals("0"))))
            if (i == Word.length() - 1)
              tmpSign = false;
            else {
              String tmpNextStr = Word.substring(i + 1, i + 2);
              if (!(nUnit.containsKey(tmpNextStr)))
                tmpSign = false;

            }


          if (tmpSign)
          {
            tempNumber = DoubleToStr(Double.parseDouble(tempNumber) + Double.parseDouble(NumberStr.get(tmpStr).toString()));
          }
          else {
            String tmpO = "";
            for (int o = 1; o < lastUnitLevel; ++o)
              tmpO = tmpO + "0";

            tempNumber = DoubleToStr(Double.parseDouble(tempNumber) + Double.parseDouble(NumberStr.get(tmpStr).toString() + tmpO));
          }
          unitSign = false;
        }
        else
        {
          int currentUnitLevel = nUnit.get(tmpStr).toString().length();

          if ((lastUnitLevel > currentUnitLevel) || (lastUnitLevel == 0))
          {
            tempNumber = tempNumber + nUnit.get(tmpStr);
            tempNumberArray.put(tempNumber, nUnit.get(tmpStr).toString());
            tempNumber = "";
          }
          else
          {
            tempNumber = NumberStrPlus(tempNumber, currentUnitLevel, tempNumberArray);
            tempNumber = tempNumber + nUnit.get(tmpStr);
            tempNumberArray.put(tempNumber, nUnit.get(tmpStr).toString());
            tempNumber = "";
          }

          lastUnitLevel = currentUnitLevel;
          unitSign = true;
        }

        if (i == Word.length() - 1)
        {
          tempNumber = NumberStrPlus(tempNumber, 999, tempNumberArray);
          tempWord = tempWord + tempNumber;
          tempNumber = "";
        }
        numSign = true;
      }
      else
      {
        if (numSign) {
          tempNumber = NumberStrPlus(tempNumber, 999, tempNumberArray);
          tempWord = tempWord + tempNumber + tmpStr;
        }
        else tempWord = tempWord + tmpStr;

        tempNumberArray = new Hashtable();
        tempNumber = "";
        lastUnitLevel = 0;
        unitSign = false;
        numSign = false;
      }

    }

    return tempWord;
  }

  private static String DoubleToStr(double xNum)
  {
    DecimalFormat dFormat = new DecimalFormat("#0");
    return dFormat.format(xNum);
  }

  private static String NumberStrPlus(String tempNumber, int currentUnitLevel, Hashtable tempNumberArray)
  {
    double tmpD = 0D;
    Hashtable tmpHt = new Hashtable();

    for (Iterator itNumber = tempNumberArray.keySet().iterator(); itNumber.hasNext(); ) {
      String key = (String)itNumber.next();
      String value = tempNumberArray.get(key).toString();
      if (value.length() <= currentUnitLevel)
        tmpD = tmpD + Double.parseDouble(key);
      else
        tmpHt.put(key, value);
    }

    tempNumberArray = tmpHt;

    if (tempNumber.length() < 1)
      tempNumber = tempNumber + "0";

    return DoubleToStr(tmpD + Double.parseDouble(tempNumber));
  }

  public static void main(String[] args)
  {
    try {
      System.out.println("������Ҫת�������֣�");
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String s = br.readLine();

      long startTime = System.currentTimeMillis();
      String result = "";
      int runTimes = 1;
      for (int i = 0; i < runTimes; ++i) {
        result = operate(s, 0);
      }

      long endTime = System.currentTimeMillis();
      double times = runTimes * 1000 / (endTime - startTime);
      System.out.println(s + " = " + result + "(��ʱ��" + (endTime - startTime) + "����, ÿ��ִ��" + times + "��)");
    }
    catch (IOException e)
    {
    }
  }

  static
  {
    NumberStr_Simp.put("��", "0");
    NumberStr_Simp.put("��", "0");
    NumberStr_Simp.put("һ", "1");
    NumberStr_Simp.put("��", "2");
    NumberStr_Simp.put("��", "3");
    NumberStr_Simp.put("��", "4");
    NumberStr_Simp.put("��", "5");
    NumberStr_Simp.put("��", "6");
    NumberStr_Simp.put("��", "7");
    NumberStr_Simp.put("��", "8");
    NumberStr_Simp.put("��", "9");
    NumberStr_Simp.put("ʮ", "10");

    NumberStr_All = new Hashtable();
    NumberStr_All.put("��", "0");
    NumberStr_All.put("��", "0");
    NumberStr_All.put("��", "0");
    NumberStr_All.put("һ", "1");
    NumberStr_All.put("��", "2");
    NumberStr_All.put("��", "3");
    NumberStr_All.put("��", "4");
    NumberStr_All.put("��", "5");
    NumberStr_All.put("��", "6");
    NumberStr_All.put("��", "7");
    NumberStr_All.put("��", "8");
    NumberStr_All.put("��", "9");
    NumberStr_All.put("ʮ", "10");
    NumberStr_All.put("Ҽ", "1");
    NumberStr_All.put("��", "2");
    NumberStr_All.put("��", "3");
    NumberStr_All.put("��", "4");
    NumberStr_All.put("��", "5");
    NumberStr_All.put("½", "6");
    NumberStr_All.put("��", "7");
    NumberStr_All.put("��", "8");
    NumberStr_All.put("��", "9");
    NumberStr_All.put("ʰ", "10");

    nUnit_Simp = new Hashtable();
    nUnit_Simp.put("ʮ", "0");
    nUnit_Simp.put("��", "00");
    nUnit_Simp.put("ǧ", "000");
    nUnit_Simp.put("��", "0000");
    nUnit_Simp.put("��", "00000000");
    nUnit_Simp.put("��", "0000000000000000");

    nUnit_All = new Hashtable();
    nUnit_All.put("ʮ", "0");
    nUnit_All.put("��", "00");
    nUnit_All.put("ǧ", "000");
    nUnit_All.put("��", "0000");
    nUnit_All.put("��", "00000000");
    nUnit_All.put("��", "0000000000000000");
    nUnit_All.put("ʰ", "0");
    nUnit_All.put("��", "00");
    nUnit_All.put("Ǫ", "000");
  }
}