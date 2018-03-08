package br.com.project_poc_car.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author alan.franco
 *
 */
public final class SourceUtility {

	public SourceUtility() {
		super();
	}

	public static String formatDateToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(date);
	}

	public static String formatDateAndTimeToString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return dateFormat.format(date);
	}

	public static String formatZipCode(String zipCode) {
		if (zipCode.trim().length() != 8) {
			return "";

		}
		return zipCode.substring(0, 2) + "." + zipCode.substring(2, 5) + "-" + zipCode.substring(5, 8);
	}

	public static BigDecimal convertDoubleToBigDecimal(Double number) {
		BigDecimal bigDecNumber = null;
		if (number != null) {
			bigDecNumber = new BigDecimal(number);
		}
		return bigDecNumber;
	}

	public static void ziparPasta(String caminhoAbsolutoPasta, String destinoArquivoZipado) throws Exception {
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;

		fileWriter = new FileOutputStream(destinoArquivoZipado);
		zip = new ZipOutputStream(new BufferedOutputStream(fileWriter));

		adicionarPastaNoZip("", caminhoAbsolutoPasta, zip);
		zip.flush();
		zip.close();
	}

	private static void adicionarArquivosNoZip(String path, String srcFile, ZipOutputStream zip) throws Exception {
		File folder = new File(srcFile);
		if (folder.isDirectory()) {
			adicionarPastaNoZip(path, srcFile, zip);
		} else {
			byte[] buf = new byte[1024];
			int len;
			FileInputStream in = new FileInputStream(srcFile);
			zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
			while ((len = in.read(buf)) > 0) {
				zip.write(buf, 0, len);
			}
		}
	}

	private static void adicionarPastaNoZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
		File folder = new File(srcFolder);

		for (String fileName : folder.list()) {
			if (path.equals("")) {
				adicionarArquivosNoZip(folder.getName(), srcFolder + "/" + fileName, zip);
			} else {
				adicionarArquivosNoZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
			}
		}
	}

	public static void descompactarArquivoZip(String caminhoArqZip, String localParaDescompactar) throws IOException {
		InputStream arqZip = new FileInputStream(caminhoArqZip);
		ZipInputStream zin = new ZipInputStream(arqZip);

		try {
			ZipEntry entry;
			while ((entry = zin.getNextEntry()) != null) {
				if (!localParaDescompactar.endsWith("/"))
					localParaDescompactar += "/";

				if (entry.isDirectory()) {
					(new File(localParaDescompactar + entry.getName())).mkdir();
					continue;
				}

				FileOutputStream out = null;
				try {
					out = new FileOutputStream(localParaDescompactar + entry.getName());

					byte[] b = new byte[512];
					int len = 0;

					while ((len = zin.read(b)) != -1) {
						out.write(b, 0, len);
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (out != null)
						out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (zin != null)
				zin.close();
		}
	}

	public static String regexToRemoveSpecialCharactersLessTraces(String string) {
		String finalString = null;
		if (string != null) {
			finalString = string.replaceAll("[^a-zA-Z1-9_-]", "");
		}
		return finalString;
	}

	public static String replaceAllBreakLinesAndBlankSpacesForUnderLine(String string) {
		String finalString = null;
		if (string != null) {
			finalString = string.replaceAll("\n", " ").replaceAll("\t", " ").replaceAll("\r", " ").replaceAll(" ", "_");
		}
		return finalString;
	}

	public static String formatCharactersFromDate(String dateString) {
		String finalString = null;
		if (dateString != null) {
			finalString = dateString.replaceAll("\\D", "/");
		}
		return finalString;
	}

}
