// ISecurityCenter.aidl
package com.example.asus1.androidprmoteflighting.AIDLTest;

// Declare any non-default types here with import statements

interface ISecurityCenter {
   String encrypt(String content);
   String decrypt(String password);
}
