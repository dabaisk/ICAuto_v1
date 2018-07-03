package cn.dabaisk.icauto.test;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

/** 
* @ClassName: IEBrower 
* @Description 类的作用: 
* @author 作者:一叶扁舟(skiff)
* @date 2018年3月7日 上午10:25:20 
*  
*/
public class IEBrower extends JPanel {  
  
    private static final long serialVersionUID = 1L;  
  
    private JPanel webBrowserPanel;  
  
    private JWebBrowser webBrowser;  
  
     private String url;  
  
    public IEBrower(String url) {  
        super(new BorderLayout());  
        this.url = url;  
        
        webBrowserPanel = new JPanel(new BorderLayout());  
        webBrowser = new JWebBrowser();  
        webBrowser.navigate(url);  
        webBrowser.setButtonBarVisible(false);  
        webBrowser.setMenuBarVisible(false);  
        webBrowser.setBarsVisible(false);  
        webBrowser.setStatusBarVisible(false);  
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);  
        add(webBrowserPanel, BorderLayout.CENTER);  
        // webBrowser.executeJavascript("javascrpit:window.location.href='http://www.baidu.com'");  
       //  webBrowser.executeJavascript("alert('haha')"); //执行Js代码  
    }  
  
    public static void main(String[] args) {  
        final String url = "http://ic5999.com/login";  
        final String title = "物资网";  
        UIUtils.setPreferredLookAndFeel();  
        NativeInterface.open();  
  
        SwingUtilities.invokeLater(new Runnable() {  
            public void run() {  
                JFrame frame = new JFrame(title);  
              //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
                //禁用close功能
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);        
                //不显示标题栏,最大化,最小化,退出按钮
                frame.setUndecorated(true);                                        
                frame.getContentPane().add(new IEBrower(url), BorderLayout.CENTER);  
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  
                frame.setLocationByPlatform(true);  
                frame.setVisible(true);  
                //禁用最大化
              //  frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG );//使frame只剩下标题栏
            }  
        });  
        NativeInterface.runEventPump();  
    }  
  
}  
