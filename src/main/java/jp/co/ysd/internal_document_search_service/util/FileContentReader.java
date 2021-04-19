package jp.co.ysd.internal_document_search_service.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author yuichi
 *
 */
public final class FileContentReader {

	public static String read(Path path) throws IOException {
		return Files.readAllLines(path).stream().reduce((prev, next) -> prev + "\n" + next).get();
	}

}
