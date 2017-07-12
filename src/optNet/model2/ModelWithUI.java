package optNet.model2;

import sim.portrayal.continuous.*;
import sim.engine.*;
import sim.display.*;
import sim.portrayal.simple.*;
import sim.portrayal.*;
import javax.swing.*;
import java.awt.Color;

public class ModelWithUI extends GUIState {

	    public Display2D display;
	    public JFrame displayFrame;

	    ContinuousPortrayal2D modelFieldPortrayal = new ContinuousPortrayal2D();

	    public static void main(String[] args) {
	        ModelWithUI vid = new ModelWithUI();
	        Console c = new Console(vid);
	        c.setVisible(true);
	    }

	    public ModelWithUI() { super(new Model( System.currentTimeMillis())); }
	    public ModelWithUI(SimState state) { super(state); }

	    public Object getSimulationInspectedObject() { return state; }

	    public Inspector getInspector() {
	        Inspector i = super.getInspector();
	        i.setVolatile(true);
	        return i;
	    }

	    public static String getName() { return "Tesi Tommaso Giovannelli - OptNET"; }
	    
	    public static Object getInfo() {
	    	return "<H2>Tesi - Tommaso Giovannelli</H2><p>OptNET";
	    }
	    
	    public void start() {
	        super.start();
	        setupPortrayals();
	    }

	    public void load(SimState state) {
	        super.load(state);
	        setupPortrayals();
	    }

	    public void setupPortrayals() {

	        Model model = (Model) state;
	        
	        // tell the portrayals what to portray and how to portray them
	        modelFieldPortrayal.setField( model.modelField );
	        modelFieldPortrayal.setPortrayalForClass(CAP.class, new OvalPortrayal2D(Color.BLUE));
	        modelFieldPortrayal.setPortrayalForClass(DFL.class, new HexagonalPortrayal2D(Color.GREEN));
	        modelFieldPortrayal.setPortrayalForClass(DFT.class, new HexagonalPortrayal2D(Color.YELLOW));
	        modelFieldPortrayal.setPortrayalForClass(Plant.class, new RectanglePortrayal2D(Color.ORANGE));
	                                                
	        // reschedule the displayer
	        display.reset();
	        display.setBackdrop(Color.gray);

	        // redraw the display
	        display.repaint();	                
	                
	    }

	    public void init(Controller c) {
	    	
	        super.init(c);

	        // make the displayer
	        display = new Display2D(600,600,this);
	        // turn off clipping
	        display.setClipping(false);

	        displayFrame = display.createFrame();
	        displayFrame.setTitle("Mappa degli impianti");
	        c.registerFrame(displayFrame);   // register the frame so it appears in the "Display" list
	        displayFrame.setVisible(true);
	        display.attach(modelFieldPortrayal, "ModelField" );
	        
	    }

	    public void quit() {
	    	
	        super.quit();

	        if (displayFrame!=null) displayFrame.dispose();
	        displayFrame = null;
	        display = null;
	     
	    }


}
