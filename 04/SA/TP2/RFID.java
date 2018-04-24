import com.phidget22.*;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import sun.reflect.annotation.ExceptionProxy;

import java.util.UUID;

public class RFIDExample {

    public static final void main(String args[]) throws Exception {
        //Enable logging to stdout
        com.phidget22.Log.enable(LogLevel.INFO, null);

        RFID ch = new RFID();

        ch.addAttachListener(new AttachListener() {
            public void onAttach(AttachEvent ae) {
                RFID phid = (RFID) ae.getSource();
                try {
                    if(phid.getDeviceClass() != DeviceClass.VINT){
                        System.out.println("channel " + phid.getChannel() + " on device " + phid.getDeviceSerialNumber() + " attached");
                    }
                    else{
                        System.out.println("channel " + phid.getChannel() + " on device " + phid.getDeviceSerialNumber() + " hub port " + phid.getHubPort() + " attached");
                    }
                } catch (PhidgetException ex) {
                    System.out.println(ex.getDescription());
                }
            }
        });

        ch.addDetachListener(new DetachListener() {
            public void onDetach(DetachEvent de) {
                RFID phid = (RFID) de.getSource();
                try {
                    if (phid.getDeviceClass() != DeviceClass.VINT) {
                        System.out.println("channel " + phid.getChannel() + " on device " + phid.getDeviceSerialNumber() + " detached");
                    } else {
                        System.out.println("channel " + phid.getChannel() + " on device " + phid.getDeviceSerialNumber() + " hub port " + phid.getHubPort() + " detached");
                    }
                } catch (PhidgetException ex) {
                    System.out.println(ex.getDescription());
                }
            }
        });

        ch.addErrorListener(new ErrorListener() {
            public void onError(ErrorEvent ee) {
                System.out.println("Error: " + ee.getDescription());
            }
        });

        ch.addTagListener(new RFIDTagListener() {
            public void onTag(RFIDTagEvent e) {

                System.out.println("Tag read: " + e.getTag());

                //Adafruit
                String clientId = "Mikeeee";
                String content = e.getTag();
                String topic = "MikeAdaFruitIO";
                int qos = 0;
                boolean retained = false;
                String broker = "tcp://io.adafruit.com:1883";
                String feed = "/feeds/registocliente";
                MemoryPersistence persistence = new MemoryPersistence();

                try {
                    MqttClient client = new MqttClient(broker, clientId, persistence);
                    MqttConnectOptions connOpts = new MqttConnectOptions();
                    connOpts.setCleanSession(true);
                    connOpts.setUserName("username_here");
                    String password = "key_here";
                    connOpts.setPassword(password.toCharArray());
                    client.connect(connOpts);
                    System.out.println("Connected");
                    MqttTopic registoTopic = client.getTopic("feed_here");
                    System.out.println("Publishing message: "+content);
                    registoTopic.publish(new MqttMessage(content.getBytes()));
                    System.out.println("Published data. Topic: " + registoTopic.getName() + "  Message: " + content);
                    client.disconnect();
                    System.out.println("Disconnected");
                    System.exit(0);


                } catch(MqttException me) {
                    System.out.println("reason "+me.getReasonCode());
                    System.out.println("msg "+me.getMessage());
                    System.out.println("loc "+me.getLocalizedMessage());
                    System.out.println("cause "+me.getCause());
                    System.out.println("excep "+me);
                    me.printStackTrace();
                }

            }
        });

        ch.addTagLostListener(new RFIDTagLostListener() {
            public void onTagLost(RFIDTagLostEvent e) {
                System.out.println("Tag lost: " + e.getTag());
            }
        });

        try {
            /*
             * Please review the Phidget22 channel matching documentation for details on the device
             * and class architecture of Phidget22, and how channels are matched to device features.
             */

            /*
             * Specifies the serial number of the device to attach to.
             * For VINT devices, this is the hub serial number.
             *
             * The default is any device.
             */
            //ch.setDeviceSerialNumber(<YOUR DEVICE SERIAL NUMBER>);
            /*
             * For VINT devices, this specifies the port the VINT device must be plugged into.
             *
             * The default is any port.
             */
            //ch.setHubPort(0);

            /*
             * Specifies that the channel should only match a VINT hub port.
             * The only valid channel id is 0.
             *
             * The default is 0 (false), meaning VINT hub ports will never match
             */
            //ch.setIsHubPortDevice(true);

            /*
             * Specifies which channel to attach to.  It is important that the channel of
             * the device is the same class as the channel that is being opened.
             *
             * The default is any channel.
             */
            //ch.setChannel(0);

            /*
             * In order to attach to a network Phidget, the program must connect to a Phidget22 Network Server.
             * In a normal environment this can be done automatically by enabling server discovery, which
             * will cause the client to discovery and connect to available servers.
             *
             * To force the channel to only match a network Phidget, set remote to 1.
             */
            // Net.enableServerDiscovery(ServerType.DEVICE_REMOTE);
            // ch.setIsRemote(true);

            System.out.println("Opening and waiting 5 seconds for attachment...");
            ch.open();

            System.out.println("\n\nGathering data for 10000 seconds\n\n");
            Thread.sleep(10000000);

            ch.close();
            System.out.println("\nClosed RFID");

        } catch (PhidgetException ex) {
            System.out.println(ex.getDescription());
        }
    }
}
