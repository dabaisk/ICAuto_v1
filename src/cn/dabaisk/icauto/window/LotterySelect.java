package cn.dabaisk.icauto.window;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import cn.dabaisk.icauto.brower.ChromeBrower;
import cn.dabaisk.icauto.callback.CallBack;
import cn.dabaisk.icauto.service.LotterySelectService;

public class LotterySelect extends JFrame {
	ChromeBrower brower = new ChromeBrower();
	private static final long serialVersionUID = 1L;
	JTextField LotteryCode = new JTextField();
	JTextField LotteryType = new JTextField();
	JComboBox<Integer> numPeriods = null; // 下拉框
	JButton bl = new JButton("查询");
	JButton bg = new JButton("关闭");
	private LotterySelectService lotterySelectService = new LotterySelectService();

	public LotterySelectService getLotterySelectService() {
		return lotterySelectService;
	}

	public void setLotterySelectService(LotterySelectService lotterySelectService) {
		this.lotterySelectService = lotterySelectService;
	}

	// 构造无参构造器把主要的方法放在构造器里,然后在main方法里面调
	public LotterySelect() {
		setBounds(25, 25, 250, 250);
		Container c = getContentPane();
		c.setLayout(new GridLayout(4, 2, 10, 10));
		c.add(new JLabel("彩种类型"));
		LotteryType.setText("Ssc5X");
		c.add(LotteryType);
		c.add(new JLabel("彩种编码"));
		LotteryCode.setText("1008");
		c.add(LotteryCode);
		JLabel numPeriodsJp = new JLabel("开奖期数");
		Integer numPeriodsJps[] = { 15, 20, 25, 30, 50, 200 };
		numPeriods = new JComboBox<>(numPeriodsJps);
		c.add(numPeriodsJp);
		c.add(numPeriods);
		c.add(bl);
		c.add(bg);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		// 注意：此处是匿名内部类
		bg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.nextFocus();
			}
		});

	}

	/*
	 * 回调函数
	 */
	public void call(CallBack a) {
		/*
		 * b help a solve the priblem
		 */
		System.out.println("响应回调函数");
		/*
		 * call back
		 */
		// 注意：此处是匿名内部类
		bl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String code = LotteryCode.getText();
				String type = LotteryType.getText();
				Object numPeriod = numPeriods.getSelectedItem();
				Map<String, String> map = new LinkedHashMap<>();
				map.put("LotteryCode", code);
				map.put("type", type);
				map.put("numPeriods", numPeriod + "");
				lotterySelectService.status = "ok";
				lotterySelectService.reqObject = map;
				System.out.println(code + "---" + type + "----" + numPeriod);
				// 发起请求唤起选择彩种后续操作
				a.slove();
			}

		});

	}

	public static void main(String[] args) {
		new LotterySelect();
	}
}
