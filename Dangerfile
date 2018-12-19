## WARNINGS:
if (github.pr_body + github.pr_title).include?("WIP")
  warn("Pull Request is Work in Progress")
end

can_merge = github.pr_json["mergeable"]
warn("This PR cannot be merged yet.", sticky: false) unless can_merge

## TODOs:
todoist.warn_for_todos
todoist.print_todos_table

## GRADLE ERROR:
pathToGradleError = "gradle_error"
gradleErrorFileExists = File.exist?(pathToGradleError)
if gradleErrorFileExists
  error = File.read(pathToGradleError)
  fail(error)
end

## LINT:
lint_path = ENV["DANGER_LINT_PATH"]
if !lint_path.nil?
  if File.exist?(lint_path)
    android_lint.report_file = lint_path
    android_lint.filtering = true
    android_lint.skip_gradle_task = true
    android_lint.lint(inline_mode: true)
  else
    fail("Android Lint task is set but there is no report file")
  end
end

## CHECKSTYLE:
checkstyle_path = ENV["DANGER_CHECKSTYLE_PATH"]
if !checkstyle_path.nil?
  if File.exist?(checkstyle_path)
    checkstyle_format.base_path = Dir.pwd
    checkstyle_format.report checkstyle_path
  else
    fail("Checkstyle task is set but there is no report file")
  end
end

## KTLINT:
ktlint_path = ENV["DANGER_KTLINT_PATH"]
if !ktlint_path.nil?
  if File.exist?(ktlint_path)
    checkstyle_format.base_path = Dir.pwd
    checkstyle_format.report ktlint_path
  else
    fail("KTLint task is set but there is no report file")
  end
end

## DETEKT:
detekt_path = ENV["DANGER_DETEKT_PATH"]
if !detekt_path.nil?
  if File.exist?(ktlint_path)
    checkstyle_format.base_path = Dir.pwd
    checkstyle_format.report detekt_path
  else
    fail("Detekt task is set but there is no report file")
  end
end

## JUNIT:
junit_tests_dir = ENV["DANGER_JUNIT_PATH"]
if !junit_tests_dir.nil?
  test_results = Dir["#{junit_tests_dir}/*.xml"]
  if test_results.empty?
    fail("JUnit task is set but there are no report files")
  else
    test_results.each do |file_name|
      junit.parse file_name
      junit.show_skipped_tests = true
      junit.report
    end
  end
end

## JIRA:
if !ENV["DANGER_JIRA_KEY"].nil?
  jira.check(key: ENV["DANGER_JIRA_KEY"], url: "https://smartmobilefactory.atlassian.net/browse", search_commits: true)
end
