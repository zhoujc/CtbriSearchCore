package com.ctbri.srhcore.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Iterator;
/**
 * 旧系统中直接拿来
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
        if ((tmpStr.equals("十")) || (tmpStr.equals("拾")))
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
      System.out.println("请输入要转换的数字：");
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
      System.out.println(s + " = " + result + "(耗时：" + (endTime - startTime) + "毫秒, 每秒执行" + times + "次)");
    }
    catch (IOException e)
    {
    }
  }

  static
  {
    NumberStr_Simp.put("零", "0");
    NumberStr_Simp.put("○", "0");
    NumberStr_Simp.put("一", "1");
    NumberStr_Simp.put("二", "2");
    NumberStr_Simp.put("三", "3");
    NumberStr_Simp.put("四", "4");
    NumberStr_Simp.put("五", "5");
    NumberStr_Simp.put("六", "6");
    NumberStr_Simp.put("七", "7");
    NumberStr_Simp.put("八", "8");
    NumberStr_Simp.put("九", "9");
    NumberStr_Simp.put("十", "10");

    NumberStr_All = new Hashtable();
    NumberStr_All.put("零", "0");
    NumberStr_All.put("○", "0");
    NumberStr_All.put("〇", "0");
    NumberStr_All.put("一", "1");
    NumberStr_All.put("二", "2");
    NumberStr_All.put("三", "3");
    NumberStr_All.put("四", "4");
    NumberStr_All.put("五", "5");
    NumberStr_All.put("六", "6");
    NumberStr_All.put("七", "7");
    NumberStr_All.put("八", "8");
    NumberStr_All.put("九", "9");
    NumberStr_All.put("十", "10");
    NumberStr_All.put("壹", "1");
    NumberStr_All.put("贰", "2");
    NumberStr_All.put("叁", "3");
    NumberStr_All.put("肆", "4");
    NumberStr_All.put("伍", "5");
    NumberStr_All.put("陆", "6");
    NumberStr_All.put("柒", "7");
    NumberStr_All.put("捌", "8");
    NumberStr_All.put("玖", "9");
    NumberStr_All.put("拾", "10");

    nUnit_Simp = new Hashtable();
    nUnit_Simp.put("十", "0");
    nUnit_Simp.put("百", "00");
    nUnit_Simp.put("千", "000");
    nUnit_Simp.put("万", "0000");
    nUnit_Simp.put("亿", "00000000");
    nUnit_Simp.put("兆", "0000000000000000");

    nUnit_All = new Hashtable();
    nUnit_All.put("十", "0");
    nUnit_All.put("百", "00");
    nUnit_All.put("千", "000");
    nUnit_All.put("万", "0000");
    nUnit_All.put("亿", "00000000");
    nUnit_All.put("兆", "0000000000000000");
    nUnit_All.put("拾", "0");
    nUnit_All.put("佰", "00");
    nUnit_All.put("仟", "000");
  }
}