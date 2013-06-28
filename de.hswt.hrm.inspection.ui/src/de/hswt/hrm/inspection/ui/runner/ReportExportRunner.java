package de.hswt.hrm.inspection.ui.runner;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.report.latex.service.ReportService;

public class ReportExportRunner implements IRunnableWithProgress {

	@Inject
	private ReportService reportService;
	
	private Inspection inspection;

	private Path rootPath;
	
	private boolean keepFiles;
	
	public ReportExportRunner(Inspection inspection, Path rootPath,
			boolean keepFiles) {
		super();
		this.inspection = inspection;
		this.rootPath = rootPath;
		this.keepFiles = keepFiles;
	}



	@Override
	public void run(final IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
       	reportService.createReport(inspection, rootPath, keepFiles, monitor);
	}
}
