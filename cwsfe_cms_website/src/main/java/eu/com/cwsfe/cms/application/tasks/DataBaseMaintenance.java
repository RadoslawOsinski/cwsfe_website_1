package eu.com.cwsfe.cms.application.tasks;

/**
 * @author Radoslaw Osinski
 */
class DataBaseMaintenance {

    /*
    $=(@daysOfMaintenance, ["-01-01", "-05-01", "-11-01"])
$=(@executeDataBaseMaintenance, false)
$=(@date, "")
$for(@date, $@daysOfMaintenance, {
	$=(@day, $toDate($+($time.get($time.today(), "YEAR"), $@date), "yyyy-MM-dd"))
	$if($==($@day, $time.today()), {
		$if($==($sql.getConnectorType("palio"), "POSTGRESQL"), {
			$log.info("DATABASE MAINTENANCE FOR POSTGRESQL STARTED: VACUUM FREEZE")
			$sql.execute("VACUUM FREEZE")
			$log.info("DATABASE MAINTENANCE FOR POSTGRESQL ENDED: VACUUM FREEZE")
			$log.info("DATABASE MAINTENANCE FOR POSTGRESQL STARTED: VACUUM ANALYZE")
			$sql.execute("VACUUM ANALYZE")
			$log.info("DATABASE MAINTENANCE FOR POSTGRESQL ENDED: VACUUM ANALYZE")
			$=(@reindexDatabaseQuery, $+("REINDEX DATABASE ", $text.subString($sql.getUrl("palio"), $+($text.lastIndexOf($sql.getUrl("palio"), "/"), 1))))
			$log.info($+("DATABASE MAINTENANCE FOR POSTGRESQL STARTED: ", $@reindexDatabaseQuery))
			$sql.execute($@reindexDatabaseQuery)
			$log.info($+("DATABASE MAINTENANCE FOR POSTGRESQL ENDED:", $@reindexDatabaseQuery))
		})
$//		$if($==($sql.getConnectorType("palio"), ""), {	$//DB2
$//		})
$//		$if($==($sql.getConnectorType("palio"), ""), {	$//ORACLE
$//		})
$//		...
	})
})

     */
}
