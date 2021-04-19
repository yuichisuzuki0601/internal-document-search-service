package jp.co.ysd.internal_document_search_service.batch.task;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Priority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jp.co.ysd.internal_document_search_service.batch.BatchTask;
import jp.co.ysd.internal_document_search_service.model.Article;
import jp.co.ysd.internal_document_search_service.model.ScanTargetDirectory;
import jp.co.ysd.internal_document_search_service.service.ArticleService;
import jp.co.ysd.internal_document_search_service.service.ScanTargetDirectoryService;
import jp.co.ysd.internal_document_search_service.util.FileContentReader;

/**
 *
 * @author yuichi
 *
 */
@Component
@Priority(1)
public class ScanDirectoryTask extends BatchTask {

	@Value("${batch.scanDirectoryTask.execute:false}")
	private boolean execute;

	@Value("${batch.scanDirectoryTask.executeIntervalSeconds:300}")
	private int executeIntervalSeconds;

	@Autowired
	private ScanTargetDirectoryService scanTargetDirectoryService;

	@Autowired
	private ArticleService articleService;

	@Override
	protected boolean canExecute() {
		return execute;
	}

	@Override
	protected int getExecuteIntervalSeconds() {
		return executeIntervalSeconds;
	}

	@Override
	protected void execute() throws Exception {
		List<ScanTargetDirectory> stds = scanTargetDirectoryService.read();
		for (ScanTargetDirectory std : stds) {
			Path root = Paths.get(std.getPath());
			for (Path path : Files.list(root).sorted().collect(Collectors.toList())) {
				File file = path.toFile();
				if (!file.isFile() || !file.getName().endsWith("." + std.getExtension())) {
					continue;
				}
				String code = file.getName();
				if (articleService.searchByCode(code) == null) {
					Article article = new Article();
					article.setCode(code);
					article.setContent(FileContentReader.read(path));
					article.setResult(file.getCanonicalPath());
					articleService.create(article);
				}
			}
		}
	}

}
