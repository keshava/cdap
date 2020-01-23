/*
 * Copyright © 2020 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.cdap.internal.app.runtime.distributed.launcher;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 *
 */
public class TestMain {
  private static final Logger LOG = LoggerFactory.getLogger(TestMain.class);

  public static void main(String[] args) throws Exception {
    ClassLoader cl = TestMain.class.getClassLoader();
    if (!(cl instanceof URLClassLoader)) {
      throw new RuntimeException("Expect it to be a URLClassLoader");
    }
    URL[] urls = ((URLClassLoader) cl).getURLs();
    URL thisURL = TestMain.class.getClassLoader().getResource(TestMain.class.getName().replace('.', '/') + ".class");
    if (thisURL == null) {
      throw new RuntimeException("Failed to find the resource for main class");
    }
    if ("jar".equals(thisURL.getProtocol())) {
      String path = thisURL.getFile();
      thisURL = URI.create(path.substring(0, path.indexOf("!/"))).toURL();
    }

    LOG.info("This URL: {}", thisURL);

    Deque<URL> queue = new LinkedList<>(Arrays.asList(urls));
//    for (URL url : urls) {
//      LOG.info("URL: {}", url);
//      if (thisURL.equals(url)) {
//        if (url.toString().endsWith(".jar")) {
//          addAll(url, queue);
//          queue.addFirst(url);
//        }
//      } else {
//        queue.addLast(url);
//      }
//    }

    LOG.info("Classpath URLs: {}", queue);

    URLClassLoader newCL = new URLClassLoader(queue.toArray(new URL[0]), cl.getParent());
    Thread.currentThread().setContextClassLoader(newCL);
    LOG.info("### new classloader set");
    Class<?> cls = newCL.loadClass(TestLauncher.class.getName());
    LOG.info("### new classloader set 1");
    Method method = cls.getMethod("doMain");
    LOG.info("### new classloader set 2");

    LOG.info("Invoking doMain");
    method.invoke(cls.newInstance());
  }

//  private static void addAll(URL jarURL, Deque<URL> urls) throws IOException {
//    Path tempDir = Files.createTempDirectory("expanded.jar");
//    List<URL> depJars = new ArrayList<>();
//    try (JarInputStream jarInput = new JarInputStream(jarURL.openStream())) {
//      JarEntry entry = jarInput.getNextJarEntry();
//      while (entry != null) {
//        if (entry.getName().endsWith(".jar")) {
//          String name = entry.getName();
//          int idx = name.lastIndexOf("/");
//          if (idx >= 0) {
//            name = name.substring(idx + 1);
//          }
//          Path jarPath = tempDir.resolve(name);
//
//          LOG.info("Jar entry {} is expanded to {}", entry.getName(), jarPath);
//          Files.copy(jarInput, jarPath);
//          depJars.add(jarPath.toUri().toURL());
//        }
//        entry = jarInput.getNextJarEntry();
//      }
//    }
//
//    ListIterator<URL> itor = depJars.listIterator(depJars.size());
//    while (itor.hasPrevious()) {
//      urls.addFirst(itor.previous());
//    }
//  }
}
