package org.dromara.demo;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class TmpTest {


    /**
    // * Note: 类名、方法名、参数名已经指定，请勿修改
    // *
    // *
    // * 输入一个电话号码字符串，和一个字符串数组。判断字符串数组中，每个字符串能否通过【连续字符从小到大排序】的操作，整个字符串变得与给定的电话号码字符串相同，并将判定结果依次写入结果数组
    // * @param phoneNum string字符串  电话号码，纯数字
    // * @param cardListArray string字符串 一维数组 字符串数组，卡片序列组成的数组
    // * @return int整型一维数组
    // */
    //@Test
    //public void test(){
    //    String[] s = {"191","12"};
    //    int[] res = SortSubStringToBuildPhoneNum("119", s);
    //    System.out.println(Arrays.toString(res));
    //}
    //
    //
    //public int[] SortSubStringToBuildPhoneNum(String phoneNum, String[] cardListArray) {
    //    // write code here
    //    int length = phoneNum.length();
    //    Map<Character, Integer> map = new HashMap<Character, Integer>();
    //    for(int i=0; i<length; ++i){
    //        Character c = phoneNum.charAt(i);
    //        if (map.containsKey(c)){
    //            map.put(c, map.get(c)+1);
    //        }else {
    //            map.put(c, 1);
    //        }
    //    }
    //
    //    int[] res = new int[cardListArray.length];
    //    for (int i=0; i<res.length; ++i){
    //        String s = cardListArray[i];
    //        Map<Character, Integer> tmp = new HashMap<Character, Integer>();
    //        if (s.length() != length){
    //            res[i] = 0;
    //            continue;
    //        }
    //        for (int j=0; j<s.length(); ++j){
    //            Character c = s.charAt(j);
    //            if (isNum(c)){
    //                if (tmp.containsKey(c)){
    //                    tmp.put(c, tmp.get(c)+1);
    //                }else {
    //                    tmp.put(c, 1);
    //                }
    //            }else {
    //                res[i] = -1;
    //                break;
    //            }
    //        }
    //        if (res[i] == -1){
    //            continue;
    //        }
    //        if (mapIsSame(map, tmp)){
    //            res[i] = 1;
    //        }else {
    //            res[i] = 0;
    //        }
    //    }
    //
    //    return res;
    //}
    //
    //public boolean isNum(Character c){
    //    if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'){
    //        return true;
    //    }
    //    return false;
    //}
    //
    //public boolean mapIsSame(Map<Character, Integer> a, Map<Character, Integer> b){
    //    AtomicBoolean flag = new AtomicBoolean(true);
    //    a.forEach((key, value)->{
    //        if (!b.containsKey(key) || b.get(key)!=value){
    //            flag.set(false);
    //        }
    //    });
    //    return flag.get();
    //}


    @Test
    public void countOfTwos() {
        System.out.println(countOfTwos(23));
    }

    //public int countOfTwos(int n) {
    //    // write code here
    //    int count = 0;
    //    for (int i=2; i<=n; ++i){
    //        String string = String.valueOf(i);
    //        if (string.contains("2")){
    //            count++;
    //        }
    //    }
    //    return count;
    //}

    public int countOfTwos(int n) {
        // write code here
        int[] table = {1, 18, 252, 3168, 37512, 427608, 4748472};
        if (n<10){
            return 1;
        }

        String string = String.valueOf(n);
        int length = string.length();
        int start = (int)Math.pow(10, length-1);
        int count = table[length-2];

        for (int i = start; i <= n; i++) {
            String s = String.valueOf(i);
            if (s.contains("2")){
                System.out.println(s);
                count++;
            }
        }

        return count;
    }

    //@Test
    //public void test3() {
    //    System.out.println(decodeString("2{ab3{ac}}"));
    //}
    //
    //public String decodeString(String s) {
    //    // write code here
    //    LinkedList<String> stackC = new LinkedList<>();
    //    LinkedList<Integer> stackN = new LinkedList<>();
    //    stackC.addLast("");
    //    stackN.addLast(1);
    //    //String res = "";
    //    for (int i = 0; i < s.length(); i++) {
    //        Character c = s.charAt(i);
    //        if (isNum(c)){
    //            stackN.addLast(Integer.parseInt(String.valueOf(c)));
    //        }else if (c == '{'){
    //            stackC.addLast("");
    //        }else if (c == '}'){
    //            String s1 = stackC.removeLast();
    //            Integer count = stackN.removeLast();
    //            String s2 = stackC.removeLast();
    //            for (int j = 0; j < count; j++) {
    //                s2 += s1;
    //            }
    //            stackC.addLast(s2);
    //        }else{
    //            String string = stackC.removeLast();
    //            string += c;
    //            stackC.addLast(string);
    //        }
    //    }
    //    return stackC.removeLast();
    //}
    //
    //public boolean isNum(Character c){
    //    if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'){
    //        return true;
    //    }
    //    return false;
    //}

}
