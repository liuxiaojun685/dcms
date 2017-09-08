package entityGenerator;

import java.io.File;
import java.io.IOException;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class EntityHelper {
	public static void main(String[] args) throws IOException {
		File dirStub = new File("src/main/java/cn/bestsec/dcms/platform/entity/stubs");
		File dirTarget = new File("src/main/java/cn/bestsec/dcms/platform/entity");

		String[] listFiles = dirStub.list();
		for (String name : listFiles) {
			File stub = new File(dirStub, name);
			File target = new File(dirTarget, name);
			BufferedSource bufferedSource = Okio.buffer(Okio.source(stub));
			BufferedSink bufferedSink = Okio.buffer(Okio.sink(target));

			String firstLine = bufferedSource.readUtf8Line();
			bufferedSink.writeUtf8(firstLine.replace(".stubs", "") + "\n");

			while (!bufferedSource.exhausted()) {
				String readUtf8Line = bufferedSource.readUtf8Line();
				if (readUtf8Line.contains("@Column") && readUtf8Line.contains("length")
						&& !readUtf8Line.contains("unique = true") && !readUtf8Line.contains("nullable = false")) {

					readUtf8Line = readUtf8Line.replace(")", ", columnDefinition = \"\")");

				}
				System.out.println(readUtf8Line);
				bufferedSink.writeUtf8(readUtf8Line + "\n");

			}
			bufferedSource.close();

			bufferedSink.flush();
			bufferedSink.close();
		}

	}
}
