import React from "react";
import clsx from "clsx";
import styles from "./styles.module.css";
import plcGif from '@site/static/img/plc-animation.gif';
import solutionSteps from '@site/static/img/graphic_homepage.png';
import flexyCon from '@site/static/img/Flexy_connected.png';
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
    title: 'Easy Connections to Cloud Platforms!',
    imgSrc: solutionSteps,
    description: (
      <>
        You can connect your Flexy to different clouds, including Talk2M Direct
        connection to Azure IoT Hub (Microsoft) and AWS IoT (Amazon) are also
        possible, even if you want to use Talk2M, our Flexy is certified to
        connect to these other clouds.
      </>
    ),
  },
  {
    title: 'Leverage the power of Ignition to gain productivity insights',
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
