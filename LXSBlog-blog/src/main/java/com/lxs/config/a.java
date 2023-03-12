package com.lxs.config;

import java.util.Scanner;

/**
 * @user 潇洒
 * @date 2023/3/11-14:44
 */
public class a {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a= scanner.nextInt();

        int sumL=0;
        Long sum= 0L;
        for (int i = 0; i < a; i++) {
             int sa;
             sa= scanner.nextInt();
             sum=sum+ (long) sumL *sa;
             sumL+=sa;
        }
        System.out.println(sum);
    }
}
