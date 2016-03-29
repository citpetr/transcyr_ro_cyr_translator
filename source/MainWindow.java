import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.*;

/**
 * Created by Petr on 26.03.2016.
 */
public class MainWindow {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JTextArea inputArea;
    private JTextArea resultArea;
    private JButton converButton;
    private JButton clearButton;
    private JScrollPane inputScroller;
    private JScrollPane resultScroller;

    public void go() {


        frame = new JFrame("TRANSLIT to CYR converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel(new GridLayout(2,1));
        inputArea = new JTextArea();
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.addMouseListener(new ContextMenuMouseListener());
        resultArea = new JTextArea();
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.addMouseListener(new ContextMenuMouseListener());

        converButton = new JButton("Convert");
        converButton.addActionListener(new convertButtonActionListener());
        clearButton = new JButton("Clear");
        clearButton.addActionListener(new clearButtonActionListener());

        inputScroller = new JScrollPane(inputArea);
        inputScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        inputScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        resultScroller = new JScrollPane(resultArea);
        resultScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        resultScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        mainPanel.add(inputScroller);
        mainPanel.add(resultScroller);
        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
        buttonPanel = new JPanel(new GridLayout(1,2));
        buttonPanel.add(converButton);
        buttonPanel.add(clearButton);

        frame.getContentPane().add(BorderLayout.SOUTH,buttonPanel);
        //frame.getContentPane().add(BorderLayout.SOUTH,clearButton);

        frame.setSize(600,500);
        frame.setVisible(true);
        inputArea.requestFocus();

    }

    class convertButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Changer chang = new Changer();
            String changed = chang.convert(inputArea.getText());
            resultArea.setText(changed);
        }
    }

    class clearButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            inputArea.setText("");
            resultArea.setText("");
            inputArea.requestFocus();
        }
    }

    public class ContextMenuMouseListener extends MouseAdapter {

        private final static String CUTTEXT = "Вырезать";
        private final static String SELECTAllTEXT = "Выделить все";
        private final static String COPYTEXT = "Копировать";
        private final static String PASTETEXT = "Вставить";

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
                if (!(e.getSource() instanceof JTextComponent)) {
                    return;
                }

                JTextComponent textComponent = (JTextComponent) e.getSource();
                textComponent.requestFocus();
                boolean enabled = textComponent.isEnabled();
                boolean editable = textComponent.isEditable();
                boolean nonempty = !(textComponent.getText() == null || textComponent.getText().equals(""));
                boolean marked = textComponent.getSelectedText() != null;

                boolean pasteAvailable =
                        Toolkit.getDefaultToolkit().getSystemClipboard().
                                getContents(null).isDataFlavorSupported(
                                DataFlavor.stringFlavor);

                JPopupMenu popup = new JPopupMenu();

                if (enabled && editable && marked) {
                    Action cutAction = textComponent.getActionMap().get(DefaultEditorKit.cutAction);
                    if (cutAction == null) {
                        cutAction = textComponent.getActionMap().get("cut");
                    }
                    if (cutAction != null) {
                        popup.add(cutAction).setText(CUTTEXT);
                    }
                }
                if (enabled && marked) {
                    Action copyAction = textComponent.getActionMap().get(DefaultEditorKit.copyAction);
                    if (copyAction == null) {
                        copyAction = textComponent.getActionMap().get("copy");
                    }
                    if (copyAction != null) {
                        popup.add(copyAction).setText(COPYTEXT);
                    }
                }
                if (enabled && editable && pasteAvailable) {
                    Action pasteAction = textComponent.getActionMap().get(DefaultEditorKit.pasteAction);
                    if (pasteAction == null) {
                        pasteAction = textComponent.getActionMap().get("paste");
                    }
                    if (pasteAction != null) {
                        popup.add(pasteAction).setText(PASTETEXT);
                    }
                }

                if (enabled && nonempty) {
                    Action selectAllAction = textComponent.getActionMap().get(DefaultEditorKit.selectAllAction);
                    if (selectAllAction == null) {
                        selectAllAction = textComponent.getActionMap().get("selectAll");
                    }
                    if (selectAllAction != null) {
                        if (popup.getComponentCount() > 0) {
                            if (!(popup.getComponent(popup.getComponentCount() - 1) instanceof JPopupMenu.Separator)) {
                                popup.addSeparator();
                            }
                        }
                        popup.add(selectAllAction).setText(SELECTAllTEXT);
                    }

                }

                if (popup.getComponentCount() > 0) {
                    if (popup.getComponent(0) instanceof JPopupMenu.Separator) {
                        popup.remove(0);
                    }
                    if (popup.getComponent(popup.getComponentCount() - 1) instanceof JPopupMenu.Separator) {
                        popup.remove(popup.getComponentCount() - 1);
                    }


                    popup.show(e.getComponent(), e.getX(), e.getY() - popup.getSize().height);
                }
            }
        }
    }

}
