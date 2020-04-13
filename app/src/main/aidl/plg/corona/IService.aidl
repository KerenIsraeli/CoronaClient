// IService.aidl
package plg.corona;

import plg.corona.ICallback;

// Interface made for registering and unregistering corona service.
interface IService {
    void registerCallback(ICallback client);
    void unregisterCallback(ICallback client);
    void sendUpdate(int newSick, int newHealed, int newDeceased);
}
