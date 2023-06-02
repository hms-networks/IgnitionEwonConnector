import React from "react";
import clsx from "clsx";
import styles from "./styles.module.css";
import plcGif from '@site/static/img/plc-animation.gif';
import solutionSteps from '@site/static/img/ewon-connections.webp';
import flexyCon from '@site/static/img/ewon-ignition-connection-features.webp';
import ScDocusaurusConfig from '@site/ScDocusaurusConfig';

const FeatureList = [
  {
    title: 'Harness the power of your industrial machines',
    imgSrc: plcGif,
    description: (
      <>
        The Ewon Flexy is able to perform data acquisition with the following
        protocols: Modbus RTU, Modbus TCP, Uni-Telway, EtherNet/IP, DF1, FINS
        TCP, FINS Hostlink, ISO TCP, PPI, MPI, Profibus, Mitsubishi FX, Hitachi
        EH, ASCII.
      </>
    ),
  },
  {
    title: 'Easy cloud connection to Ignition',
    imgSrc: solutionSteps,
    description: (
      <>
        You can connect your Flexy to Ignition using DataMailbox historical data or live, realtime data through the
        M2Web API. Simply configure your Talk2M account and you're ready to go!
      </>
    ),
  },
  {
    title: (
      <>
        Leverage the power of the {ScDocusaurusConfig.title} to gain productivity insights
      </>
    ),
    imgSrc: flexyCon,
    description: (
      <>
        With your machine data connected via the {ScDocusaurusConfig.title}, you can now make use of the vast collection
        of features and modules to gain productivity insights, control your factory floor, and
        much more!
      </>
    ),
  },
];

function Feature({title, imgSrc, description}) {
  return (
    <div className={clsx('col col--4')}>
      <div className="text--center">
        <img className={styles.featureSvg} src={imgSrc} />
      </div>
      <div className="text--center padding-horiz--md">
        <h3>{title}</h3>
        <p>{description}</p>
      </div>
    </div>
  );
}

export default function HomepageFeatures(): JSX.Element {
  return (
    <section className={styles.features}>
      <div className="container">
        <div className="row">
            {FeatureList.map((props, idx) => (
                <Feature key={idx} {...props} />
            ))}
        </div>
      </div>
    </section>
  );
}
