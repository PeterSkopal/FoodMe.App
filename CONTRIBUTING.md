# Contributing to FoodMe.App

## Submission Guidelines

### Submit an issue
Before making any change to the code, submit an issue.

### <a name="submit-pr"></a> Submitting a Pull Request (PR)
Before you submit your Pull Request (PR) consider the following guidelines:

1. Be sure that an issue describes the problem you're fixing, or documents the design for the feature you'd like to add.
  Discussing the design up front helps to ensure that we're ready to accept your work.
1. Fork the repo.
1. Make your changes in a new git branch:

     ```shell
     git checkout -b my-fix-branch master
     ```

1. Create your patch, **including appropriate test cases**.
1. Make sure that all tests pass.

1. Push your branch to GitHub:

    ```shell
    git push origin my-fix-branch
    ```

1. In GitHub, send a pull request to `FoodMe.App:master`.
* If we suggest changes then:
  * Make the required updates.
  * Re-run the tests to ensure tests are still passing.
  * Rebase your branch and force push to your GitHub repository (this will update your Pull Request):

    ```shell
    git rebase master -i
    git push -f
    ```

That's it! Thank you for your contribution!

#### After your pull request is merged

After your pull request is merged, you can safely delete your branch and pull the changes
from the main (upstream) repository:

* Delete the remote branch on GitHub either through the GitHub web UI or your local shell as follows:

    ```shell
    git push origin --delete my-fix-branch
    ```

* Check out the master branch:

    ```shell
    git checkout master -f
    ```

* Delete the local branch:

    ```shell
    git branch -D my-fix-branch
    ```

* Update your master with the latest upstream version:

    ```shell
    git pull --ff upstream master
    ```
