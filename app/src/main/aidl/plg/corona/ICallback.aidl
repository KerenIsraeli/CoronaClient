// ICallback.aidl
package plg.corona;

// Declare any non-default types here with import statements
import plg.corona.Status;

interface ICallback {
     void onStatusReceived(in Status status);
     void onMessageReceived(String update);
     void onClearReceived();
}
