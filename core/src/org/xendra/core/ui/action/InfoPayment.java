package org.xendra.core.ui.action;

import java.awt.Frame;
import java.awt.event.ActionEvent;

import org.columba.api.gui.frame.IFrameMediator;
import org.columba.core.gui.action.AbstractColumbaAction;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.xendra.core.resourceloader.ResourceLoader;
import org.xendra.core.resourceloader.IconKeys;

public class InfoPayment extends AbstractColumbaAction{

	/**
	 * @param frameMediator
	 * @param name
	 */
	public InfoPayment(IFrameMediator frameMediator) {
		super(frameMediator, Msg.getMsg(Env.getCtx(),"InfoPayment"));

		putValue(AbstractColumbaAction.LARGE_ICON, ResourceLoader
				.getIcon(IconKeys.INFORM));
		putValue(AbstractColumbaAction.SMALL_ICON, ResourceLoader
				.getSmallIcon(IconKeys.INFORM));
	}

	public void actionPerformed(ActionEvent e) {
		int WindowNo = 0;
		org.compiere.apps.search.Info.showPayment((Frame) this.frameMediator.getView().getFrame(), WindowNo, "");
	}
}
