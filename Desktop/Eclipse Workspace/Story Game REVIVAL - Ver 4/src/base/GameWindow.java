package base;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import input.Log;
import util.Dfl;

public class GameWindow implements KeyListener
{
	private JFrame window;
	private JTextField inputTextArea;
	private JTextPane promptTextArea;
	private JTextPane outputTextArea;
	
	private GridBagConstraints c;
	
	private String selectedText;
	private String fullText;
	private String textBeforeSel;
	private String textAfterSel;
	
	private boolean justFixed;
	
	public GameWindow()
	{		
		selectedText = null;
		fullText = null;
		
		justFixed = false;
		
		//Making window.
		window = new JFrame("Story Game");
		window.setLayout(new GridBagLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(900, 650);
		window.setLocation(100, 100);
		
		//Making the prompt area.
        promptTextArea = new JTextPane();
        promptTextArea.setEditable(false);
        promptTextArea.setBackground(Color.GRAY);
        promptTextArea.setForeground(Color.BLACK);
        promptTextArea.setFont(new Font("monospaced", Font.PLAIN, 12));
        
        //Adding the prompt area to the window.
        c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        window.add(promptTextArea, c);
        
        //Making the input area.
        inputTextArea = new JTextField(100);
        inputTextArea.addKeyListener(this);
        inputTextArea.setText(GameBase.getStartingInputText());
        inputTextArea.selectAll();
        inputTextArea.setFont(new Font("monospaced", Font.PLAIN, 12));
        
        //Adding the input area to the window.
        c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1.0;
        window.add(inputTextArea, c);
        
        //Making the output area.
        outputTextArea = new JTextPane();
        outputTextArea.setEditable(false);
        outputTextArea.setForeground(Color.GRAY);
        outputTextArea.setFont(new Font("monospaced", Font.ITALIC, 12));
        outputTextArea.setBorder(new CompoundBorder(outputTextArea.getBorder(),  new EmptyBorder(10,10,10,10)));
        setOutput(Output.getStartingOutput());
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        
        promptTextArea.setText(GameBase.getStartingPromptText());
        
        //Adding the output scroll pane to the window.
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 20.0;
        window.add(scrollPane, c);
        
        //Finishing up the window.
        window.setVisible(true);
        window.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
                inputTextArea.requestFocus();
            }
        }); 
	}
	
	public void setOutput(Output output)
	{
		outputTextArea.setText("");
		appendOutput(output);
	}
	
	public void appendOutput(Output output)
	{
		SimpleAttributeSet set = new SimpleAttributeSet();
		
		for (OutputPart p : output)
		{
			StyledDocument outputAreaDoc = (StyledDocument) outputTextArea.getDocument();
	    	
			try 
		    {
		    	StyleConstants.setForeground(set, p.getColor());
		    	StyleConstants.setBold(set, p.isBold());
		    	StyleConstants.setItalic(set, p.isItalics());
				outputAreaDoc.insertString(outputAreaDoc.getLength(), p.getTextAndNewLines(), set);
			} 
		    
		    catch (BadLocationException e) 
		    {
				e.printStackTrace();
			}
		}
	}
	
	public void update()
	{
		promptTextArea.setText(GameBase.getPromptText());
		inputTextArea.selectAll();
	}

	@Override
	public void keyReleased(KeyEvent e)
	{	
		int key = e.getKeyCode();
    	
    	if (key == KeyEvent.VK_UP)
		{
    		String newText = InputHelp.getNext(textBeforeSel, selectedText);
    		inputTextArea.replaceSelection(newText);
    		int selStart = textBeforeSel.length();
    		inputTextArea.select(selStart, selStart + newText.length());
    		
    		justFixed = true;
		}
    	
    	else if (key == KeyEvent.VK_DOWN)
		{
    		String newText = InputHelp.getPrev(textBeforeSel, selectedText);
    		inputTextArea.replaceSelection(newText);
    		int selStart = textBeforeSel.length();
    		inputTextArea.select(selStart, selStart + newText.length());
    		
    		justFixed = true;
		}
    	
    	else if (key == KeyEvent.VK_SPACE)
    	{
    		if (inputTextArea.getText() != null && justFixed)
    		{
    			inputTextArea.setText(inputTextArea.getText().substring(0, inputTextArea.getText().length() - 1));
    			inputTextArea.replaceSelection(InputHelp.extend() != null ? (textBeforeSel.charAt(textBeforeSel.length() - 1) == ' ' ? "" : " ") + InputHelp.extend() : " ");
    		}
    		
    		justFixed = false;
    		
    		InputHelp.resetRotation();
    	}
    	
    	else
    	{
    		justFixed = false;
    		
    		InputHelp.resetRotation();
    	}
		
		if (key == KeyEvent.VK_ENTER)
		{
			GameBase.processInput(fullText);
			setOutput(GameBase.getOutput());
			update();
			
			InputHelp.initInputHelp();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		//Do nothing.
	}
	
	@Override
	public void keyPressed(KeyEvent arg0)
	{
		selectedText = inputTextArea.getSelectedText();
		fullText = inputTextArea.getText();
		textBeforeSel = fullText.substring(0, inputTextArea.getSelectionStart());
		textAfterSel = fullText.substring(inputTextArea.getSelectionEnd());
	}
}
