#!/bin/bash

# 指定要删除的注释内容
COMMENT="/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2008-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. \"Sonatype\" and \"Sonatype Nexus\" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */"

# 遍历当前目录下所有 .java 文件
find . -name "*.java" | while read -r file; do
  # 使用 awk 删除指定的注释内容
  awk -v comment="$COMMENT" '
    BEGIN { found=0 }
    {
      if (index($0, "/*") > 0) {
        block = $0
        while (getline > 0) {
          block = block "\n" $0
          if (index($0, "*/") > 0) break
        }
        if (block != comment) print block
        found=1
      } else if (!found) {
        print
      }
      found=0
    }
  ' "$file" > tmpfile && mv tmpfile "$file"
done

echo "指定注释内容已从所有 .java 文件中删除。"