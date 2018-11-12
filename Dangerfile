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
if !(ENV["DANGER_LINT_GRADLE_TASK"].nil? || gradleErrorFileExists)
    android_lint.filtering = true
    android_lint.gradle_task = ENV["DANGER_LINT_GRADLE_TASK"]
    android_lint.lint(inline_mode: true)
end

## CHECKSTYLE:
if !(ENV["DANGER_CHECKSTYLE_PATH"].nil?)
    checkstyle_format.base_path = Dir.pwd
    checkstyle_format.report ENV["DANGER_CHECKSTYLE_PATH"]
end

## KTLINT:
if !(ENV["DANGER_KTLINT_PATH"].nil?)
    checkstyle_format.base_path = Dir.pwd
    checkstyle_format.report ENV["DANGER_KTLINT_PATH"]
end

## JUNIT:
if !(ENV["DANGER_JUNIT_PATH"].nil?)
    junit.parse ENV["DANGER_JUNIT_PATH"]
    junit.report
end

## JIRA:
if !(ENV["DANGER_JIRA_KEY"].nil?)
    jira.check(key: ENV["DANGER_JIRA_KEY"], url: "https://smartmobilefactory.atlassian.net/browse", search_commits: true)
end