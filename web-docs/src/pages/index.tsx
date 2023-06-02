import React from "react";
import clsx from "clsx";
import Link from "@docusaurus/Link";
import useDocusaurusContext from "@docusaurus/useDocusaurusContext";
import Layout from "@theme/Layout";
import HomepageFeatures from "@site/src/components/HomepageFeatures";

import styles from "./index.module.css";
const ScDocusaurusConfig = require('../../ScDocusaurusConfig');

function HomepageHeader() {
  const { siteConfig } = useDocusaurusContext();
  return (
    <header className={clsx("hero hero--primary", styles.heroBanner)}>
      <div className="container">
        <h1 className="hero__title">{siteConfig.title}</h1>
        <p className="hero__subtitle">{siteConfig.tagline}</p>
        <br />
        <div className={styles.buttons}>
          <Link
            className="button button--secondary button--lg"
            to="/docs"
          >
            Documentation
          </Link>
          <Link
            className="button button--secondary button--lg"
            to="/docs/quick-start-guide"
          >
            Quick Start Guide
          </Link>
          <Link
            className="button button--secondary button--lg"
            to={ScDocusaurusConfig.repoLatestReleaseUrl}
          >
            Download
          </Link>
          <Link
            className="button button--secondary button--lg"
            to={ScDocusaurusConfig.repoUrl}
          >
            Source Code
          </Link>
        </div>
      </div>
    </header>
  );
}

export default function Home(): JSX.Element {
  const { siteConfig } = useDocusaurusContext();
  return (
    <Layout
      title={`Home`}
      description={ScDocusaurusConfig.meta}
    >
      <HomepageHeader />
      <main>
        <HomepageFeatures />
      </main>
    </Layout>
  );
}
