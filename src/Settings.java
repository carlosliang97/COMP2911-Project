import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Settings extends JPanel {
	private String[] text = {"Music", "SFX", "Back"};
	
	/**
	 * Constructor for Menu Panel/View
	 */
	public Settings() {
		this.setLayout(new BorderLayout());
		//this.setLayout(null);
		
		//Make new check boxes and button
		JCheckBox musicBox = new JCheckBox(text[0]);
		JCheckBox SFXBox = new JCheckBox(text[1]);
		JButton backBtn = new JButton(text[2]);

		//Make checkBox selected initially
		musicBox.setSelected(true);
		SFXBox.setSelected(true);
		
		//Set the location of each button
		int btnWidth = 200;
		int btnHeight = 50;
		int startXPos = 400 - btnWidth/2;
		int startYPos = 75;
		musicBox.setBounds(startXPos, startYPos + 100, btnWidth, btnHeight);
		SFXBox.setBounds(startXPos, startYPos + 200, btnWidth, btnHeight);
		backBtn.setBounds(startXPos, startYPos + 400, btnWidth, btnHeight);

		
		//Set button fonts
		Font gameFont = new Font("Myriad Pro Light", Font.BOLD, 20);
		musicBox.setFont(gameFont);
		SFXBox.setFont(gameFont);
		backBtn.setFont(gameFont);
		
		//Set button actions
		musicBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		
		SFXBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		
		backBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		
		//Add buttons to this
		this.add(musicBox);
		this.add(SFXBox);
		this.add(backBtn);
		
		ImageIcon titleImage = new ImageIcon("image/menu_back1.png");
		JLabel  titleLabel = new JLabel("", titleImage, JLabel.CENTER);
		this.add(titleLabel);
		titleLabel.setBounds(0, 0, 800, 600);
	}
}