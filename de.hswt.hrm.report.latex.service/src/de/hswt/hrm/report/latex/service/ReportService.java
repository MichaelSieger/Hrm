package de.hswt.hrm.report.latex.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.di.annotations.Creatable;

import com.google.common.base.Optional;

import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.photo.model.Photo;
//import de.hswt.hrm.report.parser.ReportParser;

@Creatable
public class ReportService {

	private final static String RESOURCE_BUNDLE = "de.hswt.hrm.report.latex.resource";

	private final static Path RESOURCE_DIRECTORY = Paths.get("resources/reportTemplate");

	private final static Path IMAGES_DIR = Paths.get("images");

	private final static Path REPORT_FILE = Paths.get("master.tex");

	private Inspection inspection;

	private Path targetPath;

	private boolean keepFiles;
	
	public void createReport(Inspection inspection, 
			Path targetPath, boolean keepFiles, IProgressMonitor monitor) {
		if (inspection == null) {
			return;
		}

		// TODO translate
		
		monitor.beginTask("Create and export report ...", 100);
        monitor.subTask("Initialization ...");

		this.inspection = inspection;

		this.targetPath = targetPath.resolve(inspection.getTitle().replaceAll(
				"\\s", ""));

		monitor.worked(5);
        
        monitor.subTask("Create report files ...");
		createDirectoryOrFile(RESOURCE_DIRECTORY.toString());
		monitor.worked(15);

        monitor.subTask("Extract images from DB ...");
		exportImages();
		monitor.worked(25);

        monitor.subTask("Write values from DB ...");
        parseValues();
		monitor.worked(35);

        monitor.subTask("Running latex ...");
//		runLatex();
		monitor.worked(95);

        monitor.subTask("Cleaning ...");
		clean();
		
		monitor.done();
	}

	public void createReport(Inspection inspection, 
			Path targetPath, boolean keepFiles) {
		if (inspection == null) {
			return;
		}

		this.inspection = inspection;

		this.targetPath = targetPath.resolve(inspection.getTitle().replaceAll(
				"\\s", ""));

		createDirectoryOrFile(RESOURCE_DIRECTORY.toString());
		exportImages();
        parseValues();
		runLatex();
		runLatex();
		clean();
	}

	private void clean() {
		if (keepFiles) {
			return;
		}
		
		// FIXME remove source files and directories
	}
	
	private void parseValues() {
//		ReportParser parser = new ReportParser(targetPath, inspection);
//		parser.parseData();
	}

	private void exportImages() {
		Optional<Photo> photo = inspection.getFrontpicture();
		if (photo.isPresent()) {
			exportImage(photo.get());
			System.out.println("front: " + photo.get().getName());
			System.out.println("front: " + photo.get().getLabel());
		}
		photo = inspection.getPlantpicture();
		if (photo.isPresent()) {
			exportImage(photo.get());
			System.out.println("plant: " + photo.get().getName());
			System.out.println("plant: " + photo.get().getLabel());
		}
	}

	private void exportImage(Photo image) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(targetPath.resolve(IMAGES_DIR)
					.resolve(image.getName() + ".pdf").toFile());
			fos.write(image.getBlob());
			fos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void createDirectoryOrFile(String resource) {

		URL url = Platform.getBundle(RESOURCE_BUNDLE).getEntry(resource);
		Enumeration<String> resources = Platform.getBundle(RESOURCE_BUNDLE)
				.getEntryPaths(resource);

		// should be a directory
		if (resources != null) {
			if (!resource.equals(RESOURCE_DIRECTORY)) {
				createDirectory(getPreparedRelativePath(resource));
			}
			while (resources.hasMoreElements()) {
				createDirectoryOrFile((String) resources.nextElement());
			}
			return;
		}

		createFile(url, getPreparedRelativePath(resource));
	}

	private void createDirectory(String relativePath) {
		targetPath.resolve(relativePath).toFile().mkdirs();
	}

	private void createFile(URL url, String relativePathWithName) {
		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			inputStream = url.openStream();

			// write the inputStream to a FileOutputStream
			outputStream = new FileOutputStream(targetPath.resolve(
					relativePathWithName).toFile());

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String getPreparedRelativePath(String path) {
		return path.replace(RESOURCE_DIRECTORY + "/", "");
	}

	private void runLatex() {
		System.out.println(targetPath.resolve(REPORT_FILE).toString());
		
		String[] args = new String[] {
	   			"pdflatex",
	   			"-interaction=nonstopmode",
	   			"-shell-escape",
	   			targetPath.resolve(REPORT_FILE).toString()};
		try {
            ProcessBuilder builder = new ProcessBuilder(args);
            builder.directory(targetPath.toFile());
            builder.redirectErrorStream(true);
            
            // pdflatex has to run twice
            int resultCode = builder.start().waitFor();
            if (resultCode != 0) {
                throw new RuntimeException(args[0] + " error");
            }

            resultCode = builder.start().waitFor();
            if (resultCode != 0) {
                throw new RuntimeException(args[0] + " error");
            }
        }
        catch (InterruptedException | IOException e) {
        	e.printStackTrace();
        }
	}
}
