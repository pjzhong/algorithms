package com.pjzhong;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.Test;

public class LeetCodeReadMe {

  private static boolean filter(File file, List<String> blanks) {
    if (file.isHidden()) {
      return false;
    }
    String name = file.getName();
    for (String s : blanks) {
      if (name.startsWith(s) || name.endsWith(s)) {
        return false;
      }
    }
    return true;
  }


  @Test
  public void readMe() throws Exception {
    List<String> leetCodeFilter = Arrays.asList("failed", "LeetCodeReadMe", "TreeNode");

    String userDir = System.getProperty("user.dir");
    String prefix = userDir + "/test/";
    String target = prefix + "java/com/pjzhong/leetcode";

    File leetCodeDir = new File(target);
    assertTrue(leetCodeDir.exists());

    FileFilter fileFilter = f -> filter(f, leetCodeFilter);
    LinkedList<File> files = new LinkedList<>();
    files.add(leetCodeDir);

    Pattern pattern = Pattern.compile("\\\\");
    List<String> questions = new ArrayList<>();
    while (!files.isEmpty()) {
      File f = files.poll();
      if (f.isDirectory()) {
        File[] fs = f.listFiles(fileFilter);
        if (fs != null) {
          files.addAll(Arrays.asList(fs));
        }
      } else {
        String path = pattern.matcher(f.getPath().substring(prefix.length())).replaceAll("/");
        String name = path.substring(path.lastIndexOf('/') + 1, path.length() - ".java".length());
        questions.add(String.format("- [%s](%s)\n", name, path));
      }
    }

    StringBuilder sb = new StringBuilder();
    questions.stream().sorted().forEach(sb::append);

    String f = "[LeetCode:pj_zhong](https://leetcode.com/pj_zhong/)\n\n"
        + String.format("# Questions-%s\n\n", questions.size())
        + sb.toString();

    FileOutputStream outputStream = new FileOutputStream(
        new File(userDir + "/test/README.md"));
    outputStream.write(f.getBytes(StandardCharsets.UTF_8));
    outputStream.flush();
    outputStream.close();
  }

  public void example() {
    Object obj = getObject();
    //逻辑
    //逻辑
  }

  private Object getObject() {
    return null;
  }

  private void saveObject(Object o) {

  }
}

