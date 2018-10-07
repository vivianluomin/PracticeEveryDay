// AIDL_Service.aidl
package com.example.asus1.remoteservice;

// Declare any non-default types here with import statements
import com.example.asus1.remoteservice.AIDL_Activity;
import com.example.asus1.remoteservice.MSG;

interface AIDL_Service {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    void sendMessage(in MSG msg);

    void registerListener(AIDL_Activity lisntener);


}
