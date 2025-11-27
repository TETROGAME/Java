import java.io.*;
import java.util.*;

public class RepairBillFileManager implements Closeable {
    private final RandomAccessFile raf;

    public RepairBillFileManager(String fileName) throws IOException {
        this.raf = new RandomAccessFile(fileName, "rw");
    }

    @Override
    public void close() throws IOException {
        raf.close();
    }

    public void clearFile() throws IOException {
        raf.setLength(0);
    }

    public long appendRecord(RepairBill bill) throws IOException {
        raf.seek(raf.length());
        long offset = raf.getFilePointer();

        raf.writeBoolean(false);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(bill);
        }
        byte[] data = bos.toByteArray();

        raf.writeInt(data.length);
        raf.write(data);

        return offset;
    }

    public List<RecordMeta> readAllRecordsMeta() throws IOException {
        List<RecordMeta> result = new ArrayList<>();
        raf.seek(0);
        while (true) {
            long offset = raf.getFilePointer();
            if (offset >= raf.length()) break;

            try {
                boolean deleted = raf.readBoolean();
                int length = raf.readInt();
                if (length < 0 || offset + 1 + 4 + length > raf.length()) {
                    break;
                }
                raf.seek(raf.getFilePointer() + length);

                result.add(new RecordMeta(offset, deleted, length));
            } catch (EOFException e) {
                break;
            }
        }
        return result;
    }

    public RepairBill readRecordAt(long offset) throws IOException, ClassNotFoundException {
        raf.seek(offset);
        boolean deleted = raf.readBoolean();
        int length = raf.readInt();
        if (deleted) {
            return null;
        }
        if (length < 0 || offset + 1 + 4 + length > raf.length()) {
            throw new IOException("Corrupted record at offset " + offset);
        }
        byte[] data = new byte[length];
        raf.readFully(data);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return (RepairBill) ois.readObject();
        }
    }

    public void markDeleted(long offset) throws IOException {
        raf.seek(offset);
        raf.writeBoolean(true);
    }

    public static class RecordMeta {
        public final long offset;
        public final boolean deleted;
        public final int length;

        public RecordMeta(long offset, boolean deleted, int length) {
            this.offset = offset;
            this.deleted = deleted;
            this.length = length;
        }
    }

    public Map<String, List<Long>> buildIndex(String indexField) throws IOException {
        Map<String, List<Long>> index = new HashMap<>();
        raf.seek(0);
        while (true) {
            long offset = raf.getFilePointer();
            if (offset >= raf.length()) break;

            try {
                boolean deleted = raf.readBoolean();
                int length = raf.readInt();
                if (length < 0 || offset + 1 + 4 + length > raf.length()) {
                    break;
                }

                byte[] data = new byte[length];
                raf.readFully(data);
                if (deleted) {
                    continue;
                }

                RepairBill bill;
                try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
                    bill = (RepairBill) ois.readObject();
                } catch (ClassNotFoundException e) {
                    continue;
                }

                String key;
                switch (indexField) {
                    case "firm":
                        key = bill.getFirm();
                        break;
                    case "workType":
                        key = bill.getWorkType();
                        break;
                    case "date":
                        key = RepairBill.formatDate(bill.getExecutionDate());
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown indexField: " + indexField);
                }

                index.computeIfAbsent(key, k -> new ArrayList<>()).add(offset);
            } catch (EOFException e) {
                break;
            }
        }
        return index;
    }
}