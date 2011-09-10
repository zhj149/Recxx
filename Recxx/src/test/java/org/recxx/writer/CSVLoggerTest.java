package org.recxx.writer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.test.BeanTester;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CSVLoggerTest {

	private static final String TEST_FILE_NAME = "csvLoggerTestFile.csv";

	@Mock
	private BufferedWriterManager writerManagerMock;

	@Mock
	private BufferedWriter writerMock;

	@Test
	public void gettersAndSettersShouldFunctionCorrectly() throws Exception {
		new BeanTester().testBean(CSVLogger.class);
	}

	private void givenWriterManagerReturnsWriterMock() throws Exception {
		when(writerManagerMock.open(new File(TEST_FILE_NAME))).thenReturn(writerMock);
	}

	private CSVLogger givenCSVLoggerIsCreated() throws Exception {
		CSVLogger csvLogger = new CSVLogger();
		csvLogger.setFilename(TEST_FILE_NAME);
		csvLogger.setWriterManager(writerManagerMock);
		return csvLogger;
	}

	private void givenCSVLoggerIsOpen(CSVLogger csvLogger) throws Exception {
		csvLogger.open();
	}

	private void thenWriterShouldWrite(String string) throws IOException {
		verify(writerMock).write(string);
		verifyNoMoreInteractions(writerMock);
	}

	private void thenWriterShouldWriteOnlyDelimiter(CSVLogger csvLogger) throws IOException {
		thenWriterShouldWrite(csvLogger.getDelimiter());
	}

	@Test
	public void closeShouldDelegateToBufferedWriterManager() throws Exception {
		CSVLogger csvLogger = new CSVLogger();
		csvLogger.setWriterManager(writerManagerMock);
		csvLogger.close();
		verify(writerManagerMock).close();
		verifyNoMoreInteractions(writerManagerMock);
	}

	@Test
	public void openShouldDelegateToBufferedWriterManager() throws Exception {
		CSVLogger csvLogger = givenCSVLoggerIsCreated();
		csvLogger.open();
		verify(writerManagerMock).open(new File(TEST_FILE_NAME));
		verifyNoMoreInteractions(writerManagerMock);
	}

	@Test
	public void writeWithDateShouldDelegateToWriter() throws Exception {
		CSVLogger csvLogger = givenCSVLoggerIsCreated();
		givenWriterManagerReturnsWriterMock();
		givenCSVLoggerIsOpen(csvLogger);
		Date inputDate = new Date();
		csvLogger.write(inputDate);
		thenWriterShouldWrite("" + inputDate + csvLogger.getDelimiter());
	}

	@Test
	public void writeWithNullDateShouldDelegateToWriter() throws Exception {
		CSVLogger csvLogger = givenCSVLoggerIsCreated();
		givenWriterManagerReturnsWriterMock();
		givenCSVLoggerIsOpen(csvLogger);
		Date nullInputDate = null;
		csvLogger.write(nullInputDate);
		thenWriterShouldWriteOnlyDelimiter(csvLogger);
	}

	@Test
	public void writeWithStringShouldDelegateToWriter() throws Exception {
		CSVLogger csvLogger = givenCSVLoggerIsCreated();
		givenWriterManagerReturnsWriterMock();
		givenCSVLoggerIsOpen(csvLogger);
		String inputString = "TestString";
		csvLogger.write(inputString);
		thenWriterShouldWrite("" + inputString + csvLogger.getDelimiter());
	}

	@Test
	public void writeWithNullStringShouldDelegateToWriter() throws Exception {
		CSVLogger csvLogger = givenCSVLoggerIsCreated();
		givenWriterManagerReturnsWriterMock();
		givenCSVLoggerIsOpen(csvLogger);
		String nullString = null;
		csvLogger.write(nullString);
		thenWriterShouldWriteOnlyDelimiter(csvLogger);
	}

	@Test
	public void writeWithIntShouldDelegateToWriter() throws Exception {
		CSVLogger csvLogger = givenCSVLoggerIsCreated();
		givenWriterManagerReturnsWriterMock();
		givenCSVLoggerIsOpen(csvLogger);
		int inputInt = 17;
		csvLogger.write(inputInt);
		thenWriterShouldWrite("" + inputInt + csvLogger.getDelimiter());
	}

	@Test
	public void writeWithFloatShouldDelegateToWriter() throws Exception {
		CSVLogger csvLogger = givenCSVLoggerIsCreated();
		givenWriterManagerReturnsWriterMock();
		givenCSVLoggerIsOpen(csvLogger);
		float inputFloat = 17.0f;
		csvLogger.write(inputFloat);
		thenWriterShouldWrite("" + inputFloat + csvLogger.getDelimiter());
	}

	@Test
	public void writeWithDoubleShouldDelegateToWriter() throws Exception {
		CSVLogger csvLogger = givenCSVLoggerIsCreated();
		givenWriterManagerReturnsWriterMock();
		givenCSVLoggerIsOpen(csvLogger);
		double inputDouble = 17.0;
		csvLogger.write(inputDouble);
		thenWriterShouldWrite("" + inputDouble + csvLogger.getDelimiter());
	}

	@Test
	public void toStringOnUnopenedLoggerShouldNotThrowNullPointerException() throws Exception {
		String expectedToString = "CSV File not opened yet.";
		String actualToString = new CSVLogger().toString();
		assertThat(actualToString, is(expectedToString));
	}
}