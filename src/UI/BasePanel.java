package UI;

import javax.swing.JPanel;

/** Class create to establish a structure for all panels */
public class BasePanel extends JPanel {
    private final Screen screen;
    public BasePanel(Screen screen) {
        //Panel config: size, position, bg color, layout to positions
        this.screen = screen;
        this.setBounds(0, 0,this.screen.getWidth()-16,this.screen.getHeight()-39);
        this.setLayout(null);
        this.setVisible(true);
    }

}
