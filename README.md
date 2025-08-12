# michael-simons/goodreads

## About

This repository has been moved to [Codeberg](https://codeberg.org):

https://codeberg.org/michael-simons/goodreads


From Codebergs ["About" page](https://codeberg.org/about):

> Codeberg is a non-profit, community-led effort that provides Git hosting and other services for free and open source projects.
>
> Codeberg is maintained by the non-profit organization Codeberg e.V., based in Berlin, Germany. For us, supporting the commons comes first.

Why the move after more than ten years? GitHub has been acquired by Microsoft way back in [2018](https://en.wikipedia.org/wiki/GitHub#Acquisition_by_Microsoft), but lost its independence completely in [August 2025](https://www.theverge.com/news/757461/microsoft-github-thomas-dohmke-resignation-coreai-team-transition). Moving a code hosting site close to a "CoreAI" team gives an idea about the future direction of GitHub and I am not happy with that.

I want to have agency over my Free and Open Source projects and not feed the literal machine with it.
If you can, consider supporting the non-profit behind Codeberg, too: [join.codeberg.org](https://join.codeberg.org).


## How?

In case you also want to move a repository, here's how I moved this repository:

1. Create a new local and orphaned branch: `git checkout --orphan gone`
2. Delete all content: `git rm -r --cached . && git clean -df .`
3. Added this `README.md`
4. Push the `gone` branch over the old `master` branch at GitHub: `git push -u origin gone:master --force`
5. Checkout `master` again
6. Change origin `git remote set-url origin git@codeberg.org:michael-simons/goodreads.git`
7. Took the opportunity to rename `master` to `main`: `git branch -m master main && git push -u origin main`

After that I archived the GitHub repository, effectively setting it to read-only and updated the description accordingly.

## Why not just deleting the repository?

This was a public repository. Deleting it from GitHub will cause the following to happen:

> When you delete a public repository, the oldest, active public fork is chosen to be the new upstream repository. All other repositories are forked off of this new upstream and subsequent pull requests go to this new upstream repository.

Source: [Deleting a public repository](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/working-with-forks/what-happens-to-forks-when-a-repository-is-deleted-or-changes-visibility#deleting-a-public-repository)

I don't want this to happen.
