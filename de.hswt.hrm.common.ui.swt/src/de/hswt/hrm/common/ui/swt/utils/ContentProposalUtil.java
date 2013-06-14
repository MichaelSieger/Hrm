package de.hswt.hrm.common.ui.swt.utils;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.fieldassist.ComboContentAdapter;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class ContentProposalUtil {

	private static final String LCL = "abcdefghijklmnopqrstuvwxyz";
	private static final String UCL = LCL.toUpperCase();
	private static final String NUMS = "0123456789";

	public static void enableContentProposal(Combo combo) {
		SimpleContentProposalProvider proposalProvider = null;
		ContentProposalAdapter proposalAdapter = null;
		proposalProvider = new SimpleContentProposalProvider(
					combo.getItems());
			proposalAdapter = new ContentProposalAdapter(combo,
					new ComboContentAdapter(), proposalProvider,
					getActivationKeystroke(), getAutoactivationChars());
		proposalProvider.setFiltering(true);
		proposalAdapter.setPropagateKeys(true);
		proposalAdapter
				.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);

	}

	public static void enableContentProposal(Text text, String[] proposals) {
		SimpleContentProposalProvider proposalProvider = null;
		ContentProposalAdapter proposalAdapter = null;
		proposalProvider = new SimpleContentProposalProvider(proposals);
		proposalAdapter = new ContentProposalAdapter(text,
				new TextContentAdapter(), proposalProvider,
				getActivationKeystroke(), getAutoactivationChars());
	}

	// this logic is from swt addons project
	static char[] getAutoactivationChars() {

		// To enable content proposal on deleting a char

		String delete = new String(new char[] { 8 });
		String allChars = LCL + UCL + NUMS + delete;
		return allChars.toCharArray();
	}

	static KeyStroke getActivationKeystroke() {
		KeyStroke instance = KeyStroke.getInstance(
				new Integer(SWT.CTRL).intValue(), new Integer(' ').intValue());
		return instance;
	}
}
